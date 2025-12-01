package my.edu.nottingham.keystore

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class KeystoreManager {

    private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore")

    init {
        keyStore.load(null)
    }

    companion object {
        private const val KEY_ALIAS = "my_app_key"
    }

    // Simple encryption
    fun encrypt(text: String): Pair<String, String>? {
        return try {
            val key = getKey()
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val iv = cipher.iv
            val encrypted = cipher.doFinal(text.toByteArray())
            Pair(
                Base64.encodeToString(encrypted, Base64.DEFAULT),
                Base64.encodeToString(iv, Base64.DEFAULT)
            )
        } catch (e: Exception) {
            null
        }
    }

    // Simple decryption
    fun decrypt(encryptedText: String, ivString: String): String? {
        return try {
            val key = getKey()
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val spec = GCMParameterSpec(128, Base64.decode(ivString, Base64.DEFAULT))
            cipher.init(Cipher.DECRYPT_MODE, key, spec)
            val decrypted = cipher.doFinal(Base64.decode(encryptedText, Base64.DEFAULT))
            String(decrypted)
        } catch (e: Exception) {
            null
        }
    }

    private fun getKey(): SecretKey {
        if (!keyStore.containsAlias(KEY_ALIAS)) {
            createKey()
        }
        val entry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry
        return entry.secretKey
    }

    private fun createKey() {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val builder = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
        builder.setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        builder.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        builder.setKeySize(256)
        keyGenerator.init(builder.build())
        keyGenerator.generateKey()
    }

    fun keyExists(): Boolean {
        return try {
            keyStore.containsAlias(KEY_ALIAS)
        } catch (e: Exception) {
            false
        }
    }

    fun deleteKey() {
        try {
            if (keyStore.containsAlias(KEY_ALIAS)) {
                keyStore.deleteEntry(KEY_ALIAS)
            }
        } catch (e: Exception) {
            // Ignore
        }
    }
}