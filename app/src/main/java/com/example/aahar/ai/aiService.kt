package com.example.aahar.ai

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

class aiService {
    suspend fun stuff(ingredients: String): List<String> {
        val model = Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.5-flash")
        val prompt = "Suggest 6 different meal ideas I can cook using $ingredients \n" +
                "Return only the dish names with a short description, as a list."
        val response = model.generateContent(prompt)
        Log.d("GEMINI", "Response is: ${response.text}")
        val result = (response.text ?: "").split("\n").map { it.trim() }.filter { it.isNotBlank() }
        return result
    }
}