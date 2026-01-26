package com.example.aahar.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aahar.models.Stats
import com.example.aahar.network.StatsApiClient

@Composable
fun StatsDialog(
    onDismiss: () -> Unit
) {

    var stats by remember { mutableStateOf<Stats?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    // 🔥 API CALL WHEN DIALOG OPENS
    LaunchedEffect(Unit) {
        try {
            stats = StatsApiClient.api.getGlobalStats()
        } catch (e: Exception) {
            error = e.message
        }
    }

    androidx.compose.ui.window.Dialog(
        onDismissRequest = onDismiss
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.8f)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                when {
                    error != null -> {
                        Text("Error loading stats")
                    }

                    stats == null -> {
                        Text("Fetching live stats…")
                    }

                    else -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Global COVID Stats", style = MaterialTheme.typography.titleLarge)

                            Spacer(modifier = Modifier.height(16.dp))

                            Text("Cases: ${stats!!.cases}")
                            Text("Deaths: ${stats!!.deaths}")
                            Text("Recovered: ${stats!!.recovered}")
                            Text("Active: ${stats!!.active}")
                        }
                    }
                }
            }
        }
    }
}