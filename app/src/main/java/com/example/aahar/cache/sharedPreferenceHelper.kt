package com.example.aahar.cache

import android.content.Context
import android.content.SharedPreferences

class PrefsHelper(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("aahar_prefs", Context.MODE_PRIVATE)

    fun saveFavourites(favourites: List<String>) {
        prefs.edit().putStringSet("favourites", favourites.toSet()).apply()
    }

    fun getFavourites(): List<String> {
        return prefs.getStringSet("favourites", emptySet())?.toList() ?: emptyList()
    }
}
