package com.example.aahar.ai

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import java.io.IOException

class aiService {
    suspend fun stuff(ingredients: String): List<String> {
        return try {
            val model = Firebase.ai(backend = GenerativeBackend.googleAI())
                .generativeModel("gemini-2.5-flash")

            val prompt = """
    Suggest 6 different meal ideas I can cook using $ingredients.
    Return the output **only as a list of dish names and short descriptions**.
    Do NOT include any introductory sentence like "Here are some…" or "You can try…".
    Use the format: *Dish Name* Description(with exactly one space after the dish name and no 
    leading spaces before the description). If the input contains any irrelevant, 
    offensive, filthy, or inappropriate words, ignore them and instead treat them as “fruit” before
     generating the meals. Only return safe, food-related results.
""".trimIndent()

            val response = model.generateContent(prompt)
            Log.d("GEMINI", "Response is: ${response.text}")

            (response.text ?: "").split("\n").map { it.trim() }.filter { it.isNotBlank() }

        } catch (e: IOException) {
            Log.e("GEMINI", "Network error: ${e.message}")
            listOf("No internet connection. Please try again.")
        } catch (e: Exception) {
            Log.e("GEMINI", "Error: ${e.message}")
            listOf("Something went wrong. Please try again.")
        }
    }
}