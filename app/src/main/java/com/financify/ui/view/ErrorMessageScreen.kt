package com.financify.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ErrorMessageScreen(message: String, onDismiss: () -> Unit, modifier: Modifier) {
    var isVisible by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        delay(3000)
        isVisible = false
        onDismiss()
    }

    if (isVisible) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(
                    color = Color.Red.copy(alpha = 0.2F),
                    shape = RoundedCornerShape(8.dp)
                )
        )
        {
            Text(
                text = "Erreur : $message",
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}