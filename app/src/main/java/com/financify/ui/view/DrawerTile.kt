package com.financify.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.financify.model.Screens

@Composable
fun DrawerTile(screens: Screens, onSelected: () -> Unit) {
    Column {
        Divider()
        TextButton(onClick = { onSelected() }) {
            Text(text = screens.title)
        }
    }
}