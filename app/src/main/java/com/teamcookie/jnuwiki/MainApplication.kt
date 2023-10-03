package com.teamcookie.jnuwiki

import android.app.Application
import com.teamcookie.jnuwiki.util.Prefs

class MainApplication :Application() {
    companion object{
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        prefs=Prefs(applicationContext)
        super.onCreate()
    }
}