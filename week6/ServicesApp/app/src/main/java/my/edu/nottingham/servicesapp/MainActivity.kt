package my.edu.nottingham.servicesapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import my.edu.nottingham.servicesapp.data.Prefs
import my.edu.nottingham.servicesapp.service.PersistentSyncService
import my.edu.nottingham.servicesapp.service.ensureChannel
import my.edu.nottingham.servicesapp.ui.theme.ServicesAppTheme
import my.edu.nottingham.servicesapp.work.WorkScheduler

class MainActivity : ComponentActivity() {

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ensureChannel(this)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }


        setContent {
            MaterialTheme {
                val context = LocalContext.current
                val prefs = remember { Prefs(context) }
                var summary by remember { mutableStateOf(prefs.lastSummary()) }
                var temp by remember { mutableStateOf(prefs.lastTemp()) }
                var updated by remember { mutableStateOf(prefs.lastUpdated()) }

                fun refresh() {
                    summary = prefs.lastSummary()
                    temp = prefs.lastTemp()
                    updated = prefs.lastUpdated()
                }

                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Weather Sync App", style = MaterialTheme.typography.headlineSmall)

                        // Weather status
                        Card(Modifier.fillMaxWidth()) {
                            Column(Modifier.padding(16.dp)) {
                                Text("Last Summary: $summary")
                                Text("Last Temp (°C): ${if (temp.isNaN()) "—" else "%.1f".format(temp)}")
                                Text("Last Updated: $updated")
                                Spacer(Modifier.height(8.dp))
                                Button(onClick = { refresh() }) { Text("Refresh from Prefs") }
                            }
                        }

                        // Foreground Service controls
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(onClick = { PersistentSyncService.start(context)
                                Toast.makeText(context, "Start clicked", Toast.LENGTH_SHORT).show()
                            }) {
                                Text("Start Service")
                            }
                            OutlinedButton(onClick = { PersistentSyncService.stop(context) }) {
                                Text("Stop Service")
                            }
                        }

                        // WorkManager controls
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(onClick = { WorkScheduler.schedule(context) }) {
                                Text("Schedule Periodic Sync")
                            }
                            OutlinedButton(onClick = { WorkScheduler.cancel(context) }) {
                                Text("Cancel Periodic Sync")
                            }
                        }

                        Text(
                            "Tip: The periodic sync runs every 15 minutes. " +
                                    "Toggle network to see retry behavior.",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}