package com.financify.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.financify.viewmodel.AuthViewModel

@Composable
fun ScaffoldLogin(
    authViewModel: AuthViewModel,
    openDrawer: () -> Unit,
    login: () -> Unit
) {
    val loggedUser by authViewModel.loggedUser.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()
    val successMessage by authViewModel.successMessage.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()
    Scaffold(
        topBar = {
            AppBar(
                loggedUser,
                openDrawer = { openDrawer() },
                login = {
                    login()
                }
            )
        },
        content = { paddingValues ->
            AuthScreen(
                modifier = Modifier.padding(paddingValues),
                authViewModel
            )
            errorMessage?.let {
                ErrorMessageScreen(
                    message = it,
                    onDismiss = { authViewModel.clearErrorMessage() },
                    modifier = Modifier.padding(paddingValues)
                )
            }
            successMessage?.let {
                SuccessMessageScreen(
                    message = it,
                    onDismiss = { authViewModel.clearSuccessMessage() },
                    modifier = Modifier.padding(paddingValues)
                )
            }
            if (isLoading) {
                LoadingScreen(modifier = Modifier.padding(paddingValues))
            }
        }
    )
}