package my.edu.nottingham.vulnerablemobileapp

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class InsecureWebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        setContentView(webView)

        // Vulnerable: JavaScript enabled without proper safeguards
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        // Vulnerable: Loading URL from intent without validation
        intent.getStringExtra("url")?.let { url ->
            webView.loadUrl(url)  // Potential XSS vulnerability
        }

        // Vulnerable: Overriding WebViewClient without proper URL validation
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                // UNSAFE: Loading all URLs without validation
                url?.let { view?.loadUrl(it) }
                return true
            }
        }
    }
}