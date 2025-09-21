package com.example.aahar.screens

import android.R
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.aahar.ai.aiService
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun RecipeScreen(ingredients: String) {
    val recipes = remember { mutableStateListOf<String>() }
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(ingredients) {
        scope.launch {
            val ai = aiService()
            val response: Collection<String> = ai.stuff(ingredients)
            recipes.addAll(response)
            isLoading = false
        }
    }


    Scaffold(
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = "That was all it!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Gray
                )
                recipes.forEachIndexed { index, recipe ->
                    val swipeableState = rememberSwipeableState(initialValue = 0)
                    val sizePx = with(LocalDensity.current) { 300.dp.toPx() }
                    val anchors = mapOf(0f to 0, -sizePx to -1, sizePx to 1)

                    val parts = recipe.split("*").filter { it.isNotBlank() }
                    val title = parts.getOrNull(0) ?: ""
                    val description = parts.getOrNull(1) ?: ""

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(300.dp)
                            .padding(16.dp)
                            .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                            .swipeable(
                                state = swipeableState,
                                anchors = anchors,
                                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                                orientation = Orientation.Horizontal
                            ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier.padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = description,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }

                    LaunchedEffect(swipeableState.currentValue) {
                        when (swipeableState.currentValue) {
                            1 -> { // Swiped Right
                                // Add to favourites later
                                recipes.removeAt(index)
                            }

                            -1 -> { // Swiped Left
                                // Discard
                                recipes.removeAt(index)
                            }
                        }
                    }
                }
            }
        }
    }
}
