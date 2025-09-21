package com.example.aahar.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aahar.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InputScreen(navController: NavController, onGetRecipes: (String) -> Unit) {
    Scaffold(
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(WindowInsets.ime.asPaddingValues()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_transparent),
                contentDescription = "App Logo",
                modifier = Modifier.size(220.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(12.dp))


            Text(
                text = "Drop the ingredients!",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            var ingredients by remember { mutableStateOf("") }
            var errorText by remember { mutableStateOf("") }

            OutlinedTextField(
                value = ingredients,
                onValueChange = { ingredients = it
                                errorText = "" },
                label = { Text("e.g. tomato, onion, cheese") },
                modifier = Modifier.fillMaxWidth(),
                isError = errorText.isNotEmpty()
            )

            if (errorText.isNotEmpty()) {
                Text(
                    text = errorText,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            OutlinedButton(
                onClick = {
                    if (ingredients.isBlank()) {
                        errorText = "Input can't be empty"
                    } else {
                        onGetRecipes(ingredients)
                    }
                },
                modifier = Modifier.width(256.dp)
            ) {
                Text("GO")
            }

            OutlinedButton(
                onClick = {
                    navController.navigate("favourites")
                },
                modifier = Modifier.width(256.dp)
            ) {
                Text("View Saved Items")
            }
        }
    }
}