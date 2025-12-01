package my.edu.nottingham.vulnerablemobileapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var inputText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputText = findViewById(R.id.inputText)

        // Intent-based vulnerability
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        // Vulnerable: No input validation - using Kotlin null safety but still unsafe
//        intent.getStringExtra("user_input")?.let { userInput ->
//            inputText.setText(userInput)
//        }

        // Vulnerable: Load URL from intent without validation
        intent.getStringExtra("url")?.let { url ->
            // This would typically load the URL - vulnerable to XSS
            inputText.setText(url)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }
}