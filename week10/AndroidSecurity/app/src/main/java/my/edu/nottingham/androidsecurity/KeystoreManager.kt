package my.edu.nottingham.androidsecurity

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class KeystoreManager {
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    companion object {
        private const val KEY_ALIAS = "my_app_secret_key"
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
    }

    fun encryptData(plainText: String): EncryptionResult {
        return try {
            val secretKey = getOrCreateKey()
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)

            val iv = cipher.iv
            val encryptedBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

            EncryptionResult(
                encryptedData = Base64.encodeToString(encryptedBytes, Base64.DEFAULT),
                iv = Base64.encodeToString(iv, Base64.DEFAULT)
            )
        } catch (e: Exception) {
            throw Exception("Encryption failed: ${e.message}")
        }
    }

    fun decryptData(encryptedData: String, iv: String): String {
        return try {
            val secretKey = getSecretKey()
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val spec = GCMParameterSpec(128, Base64.decode(iv, Base64.DEFAULT))
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

            val decryptedBytes = cipher.doFinal(Base64.decode(encryptedData, Base64.DEFAULT))
            String(decryptedBytes, Charsets.UTF_8)
        } catch (e: Exception) {
            throw Exception("Decryption failed: ${e.message}")
        }
    }

    private fun getOrCreateKey(): SecretKey {
        return if (keyStore.containsAlias(KEY_ALIAS)) {
            getSecretKey()
        } else {
            generateKey()
        }
    }

    private fun getSecretKey(): SecretKey {
        return (keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry).secretKey
    }

    private fun generateKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            ANDROID_KEYSTORE
        )

        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).apply {
            setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            setKeySize(256)
            setUserAuthenticationRequired(false) // Set to true if you want biometric protection
        }.build()

        keyGenerator.init(keyGenParameterSpec)
        return keyGenerator.generateKey()
    }

    fun isKeyCreated(): Boolean {
        return try {
            keyStore.containsAlias(KEY_ALIAS)
        } catch (e: Exception) {
            false
        }
    }

    fun clearKey() {
        try {
            if (keyStore.containsAlias(KEY_ALIAS)) {
                keyStore.deleteEntry(KEY_ALIAS)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

data class EncryptionResult(
    val encryptedData: String,
    val iv: String
)
