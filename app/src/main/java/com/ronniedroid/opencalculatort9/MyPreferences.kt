package com.ronniedroid.opencalculatort9

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.Gson

class MyPreferences(context: Context) {

    // https://proandroiddev.com/dark-mode-on-android-app-with-kotlin-dc759fc5f0e1
    companion object {
        private const val DARK_STATUS = "ronniedroid.opencalculatort9.DARK_STATUS"
        private const val KEY_HISTORY = "ronniedroid.opencalculatort9.HISTORY"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var darkMode = preferences.getInt(DARK_STATUS, -1)
        set(value) = preferences.edit().putInt(DARK_STATUS, value).apply()
    private var history = preferences.getString(KEY_HISTORY, null)
        set(value) = preferences.edit().putString(KEY_HISTORY, value).apply()

    fun getHistory(): MutableList<History> {
        val gson = Gson()
        return if (preferences.getString(KEY_HISTORY, null) != null) {
            gson.fromJson(history, Array<History>::class.java).asList().toMutableList()
        } else {
            mutableListOf()
        }
    }

    fun saveHistory(context: Context, history: List<History>){
        val gson = Gson()
        val history2 = history.toMutableList()
        if (history2.size > 50) {
            history2.removeAt(0)
        }
        MyPreferences(context).history = gson.toJson(history2) // Convert to json
    }
}
