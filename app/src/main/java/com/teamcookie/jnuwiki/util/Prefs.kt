package com.teamcookie.jnuwiki.util

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Prefs(context: Context) {
    private val prefName="Pref"
    private val prefs = context.getSharedPreferences(prefName, MODE_PRIVATE)

    var token: String?
        get() = prefs.getString("token",null)
        set(value){
            prefs.edit().putString("token",value).apply()
        }

    var refresh: String?
        get() = prefs.getString("refresh",null)
        set(value){
            prefs.edit().putString("refresh",value).apply()
        }
}