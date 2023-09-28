package com.teamcookie.jnuwiki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.teamcookie.jnuwiki.ui.theme.JnuWikiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JnuWikiTheme {
                MainScreen()
            }
        }
    }
}