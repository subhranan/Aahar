package com.example.aahar.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aahar.cache.PrefsHelper

@Composable
fun FavouritesScreen(context: Context) {
    val prefs = PrefsHelper(context)
    val favourites = prefs.getFavourites()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Favourites",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(favourites) { item ->
                Column {
                    val trimmed = item.trim()
                    val title: String
                    val description: String

                    val regex = Regex("""^\*(.+?)\*\s*(.*)""")
                    val match = regex.find(trimmed)
                    if (match != null) {
                        title = match.groupValues[1].trim()
                        description = match.groupValues[2].trim()
                    } else {
                        val splitIndex = trimmed.indexOf(" ")
                        if (splitIndex != -1) {
                            title = trimmed.substring(0, splitIndex)
                            description = trimmed.substring(splitIndex + 1)
                        } else {
                            title = trimmed
                            description = ""
                        }
                    }

                    Text(text = title, style = MaterialTheme.typography.titleMedium)
                    if (description.isNotEmpty()) {
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        thickness = DividerDefaults.Thickness,
                        color = DividerDefaults.color
                    )
                }
            }
        }
    }
}