package com.teamcookie.jnuwiki.ui.create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.teamcookie.jnuwiki.ui.theme.JnuWikiTheme

@Composable
fun CreateScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "create",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun TopBar() {}
