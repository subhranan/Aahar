package com.example.aahar.ai

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend

class aiService {
    suspend fun stuff(ingredients: String): List<String> {
        val model = Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.5-flash")
        val prompt = """
    Suggest 6 different meal ideas I can cook using $ingredients.
    Return the output **only as a list of dish names and short descriptions**.
    Do NOT include any introductory sentence like "Here are some…" or "You can try…".
    Use the format: *Dish Name* Description
""".trimIndent()
        val response = model.generateContent(prompt)
        Log.d("GEMINI", "Response is: ${response.text}")
        val result = (response.text ?: "").split("\n").map { it.trim() }.filter { it.isNotBlank() }
        return result
    }
}