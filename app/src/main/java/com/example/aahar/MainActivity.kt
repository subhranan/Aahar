package com.example.aahar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aahar.screens.InputScreen
import com.example.aahar.screens.RecipeScreen
import com.example.aahar.ui.theme.AaharTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AaharTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "input"
                ) {
                    composable("input") {
                        InputScreen(
                            onGetRecipes = { ingredients ->
                                navController.navigate("recipes")
                            }
                        )
                    }
                    composable("recipes") {
                        RecipeScreen()
                    }
                }
            }
        }
    }
}