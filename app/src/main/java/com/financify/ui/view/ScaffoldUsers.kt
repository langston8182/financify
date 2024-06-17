package com.financify.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.financify.viewmodel.AuthViewModel
import com.financify.viewmodel.UsersViewModel

@Composable
fun ScaffoldUsers(
    usersViewModel: UsersViewModel,
    authViewModel: AuthViewModel,
    openDrawer: () -> Unit,
    login: () -> Unit
) {
    LaunchedEffect(Unit) {
        usersViewModel.fetchUsers()
    }

    val users by usersViewModel.users.collectAsState()
    val loggedUser by authViewModel.loggedUser.collectAsState()
    val errorMessage by usersViewModel.errorMessage.collectAsState()
    val successMessage by authViewModel.successMessage.collectAsState()
    val isLoading by usersViewModel.isLoading.collectAsState()
    Scaffold(
        topBar = {
            AppBar(
                loggedUser = loggedUser,
                openDrawer = { openDrawer() },
                login = { login() }
            )
        },
        content = { paddingValues ->
            UsersScreen(
                modifier = Modifier.padding(paddingValues),
                users = users ?: listOf()
            )
            errorMessage?.let {
                ErrorMessageScreen(
                    message = it,
                    onDismiss = { usersViewModel.clearErrorMessage() },
                    modifier = Modifier.padding(paddingValues)
                )
            }
            if (isLoading) {
                LoadingScreen(modifier = Modifier.padding(paddingValues))
            }
        }
    )
}