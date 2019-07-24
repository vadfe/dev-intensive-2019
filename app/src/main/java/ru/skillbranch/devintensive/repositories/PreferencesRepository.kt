package ru.skillbranch.devintensive.repositories

import android.content.SharedPreferences
import android.preference.PreferenceManager
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.models.Profile

object PreferencesRepository {

    private const val FIRST_NAME = "FIRST_NAME"
    private const val LAST_NAME = "LAST_NAME"
    private const val ABOUT = "ABOUT"
    private const val REPOSITORY = "REPOSITORY"
    private const val RATING = "RATING"
    private const val RESPECT = "RESPECT"


    private val prefs:SharedPreferences by lazy {
        val ctx = App.ApplicationContext()
        PreferenceManager.getDefaultSharedPreferences(ctx)
    }
    fun getProfile(): Profile? {
        return null
    }

    fun saveProfile(profile: Profile) {

    }
    private fun putValue(pair:Pair<String, Any>) = with(prefs.edit()){
        val key = pair.first
        val value = pair.second
        when(value){
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            else -> error("Only primitives types can be stored")
        }
        apply()
    }
}