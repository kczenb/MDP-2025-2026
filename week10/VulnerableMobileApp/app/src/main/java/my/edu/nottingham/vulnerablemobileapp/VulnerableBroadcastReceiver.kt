package my.edu.nottingham.vulnerablemobileapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class VulnerableBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Vulnerable: No permission check, accepts any broadcast
        intent.getStringExtra("secret_data")?.let { data ->
            // In real app, this might expose sensitive information
            Toast.makeText(context, "Received: $data", Toast.LENGTH_SHORT).show()
        }
    }
}