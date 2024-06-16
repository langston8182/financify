package com.financify.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.financify.model.Screens
import com.financify.viewmodel.AuthViewModel

@Composable
fun DrawerMenu(authViewModel: AuthViewModel, onSelected: (Screens) -> Unit) {
    val loggedUser by authViewModel.loggedUser.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DrawerTile(
            screens = Screens.WELCOME,
            onSelected = { onSelected(Screens.WELCOME) }
        )
        if (loggedUser != null) {
            DrawerTile(
                screens = Screens.LOGOUT,
                onSelected = { onSelected(Screens.LOGOUT) }
            )
        } else {
            DrawerTile(
                screens = Screens.LOGIN,
                onSelected = { onSelected(Screens.LOGIN) }
            )
        }
        DrawerTile(
            screens = Screens.USERS,
            onSelected = { onSelected(Screens.USERS) }
        )
    }
}