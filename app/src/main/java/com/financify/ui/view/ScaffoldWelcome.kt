package com.financify.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.financify.viewmodel.AuthViewModel

@Composable
fun ScaffoldWelcome(
    authViewModel: AuthViewModel,
    openDrawer: () -> Unit,
    login: () -> Unit
) {
    val loggedUser by authViewModel.loggedUser.collectAsState()
    val successMessage by authViewModel.successMessage.collectAsState()
    Scaffold(
        topBar = {
            AppBar(
                loggedUser = loggedUser,
                openDrawer = { openDrawer() },
                login = { login() }
            )
        },
        content = { paddingValues ->
            Welcome(modifier = Modifier.padding(paddingValues))
            successMessage?.let {
                SuccessMessageScreen(
                    message = it,
                    onDismiss = { authViewModel.clearSuccessMessage() },
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    )
}