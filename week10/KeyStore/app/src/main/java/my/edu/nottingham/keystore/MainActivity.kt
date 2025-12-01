package my.edu.nottingham.keystore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import my.edu.nottingham.keystore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var manager: KeystoreManager

    private var encryptedData: String? = null
    private var ivData: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        manager = KeystoreManager()
        setupButtons()
        updateStatus()
    }

    private fun setupButtons() {
        binding.btnEncrypt.setOnClickListener { encrypt() }
        binding.btnDecrypt.setOnClickListener { decrypt() }
        binding.btnClearKey.setOnClickListener { clearKey() }
        binding.btnCheckKey.setOnClickListener { checkKey() }
    }

    private fun encrypt() {
        val text = binding.etInput.text.toString()
        if (text.isEmpty()) {
            showToast("Enter some text first")
            return
        }

        val result = manager.encrypt(text)
        if (result != null) {
            encryptedData = result.first
            ivData = result.second
            binding.tvEncryptedData.text = "Encrypted: ${encryptedData!!.take(30)}..."
            binding.tvDecryptedData.text = "Decrypted: "
            showToast("Encrypted successfully!")
        } else {
            showToast("Encryption failed!")
        }
        updateStatus()
    }

    private fun decrypt() {
        if (encryptedData == null || ivData == null) {
            showToast("Encrypt some data first")
            return
        }

        val result = manager.decrypt(encryptedData!!, ivData!!)
        if (result != null) {
            binding.tvDecryptedData.text = "Decrypted: $result"
            showToast("Decrypted successfully!")
        } else {
            showToast("Decryption failed!")
        }
    }

    private fun clearKey() {
        manager.deleteKey()
        encryptedData = null
        ivData = null
        binding.tvEncryptedData.text = "Encrypted: "
        binding.tvDecryptedData.text = "Decrypted: "
        showToast("Key cleared!")
        updateStatus()
    }

    private fun checkKey() {
        val exists = manager.keyExists()
        showToast(if (exists) "Key exists" else "No key found")
        updateStatus()
    }

    private fun updateStatus() {
        val keyExists = manager.keyExists()
        val hasData = encryptedData != null

        binding.tvStatus.text = """
            Key: ${if (keyExists) "✓ Created" else "✗ Not Found"}
            Data: ${if (hasData) "✓ Encrypted" else "✗ No Data"}
        """.trimIndent()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}