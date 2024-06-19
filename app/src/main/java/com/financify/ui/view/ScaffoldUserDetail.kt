package com.financify.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.financify.model.Screens
import com.financify.viewmodel.AuthViewModel
import com.financify.viewmodel.UserViewModel

@Composable
fun ScaffoldUserDetail(
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    openDrawer: () -> Unit,
    login: () -> Unit,
    navController: NavController
) {
    val loggedUser by authViewModel.loggedUser.collectAsState()
    val selectedUser by userViewModel.selectedUser.collectAsState()
    val errorMessage by userViewModel.errorMessage.collectAsState()
    val successMessage by userViewModel.successMessage.collectAsState()
    val isLoading by userViewModel.isLoading.collectAsState()
    Scaffold(
        topBar = {
            AppBar(
                loggedUser = loggedUser,
                openDrawer = { openDrawer() },
                login = { login() }
            )
        },
        content = { paddingValues ->
            UserDetailScreen(
                modifier = Modifier.padding(paddingValues),
                user = selectedUser!!,
                updateGivenName = { userViewModel.updateGivenName(it) },
                updateFamilyName = { userViewModel.updateFamilyName(it) },
                updateUser = { userViewModel.updateUser() }
            )
            errorMessage?.let {
                ErrorMessageScreen(
                    message = it,
                    onDismiss = { userViewModel.clearErrorMessage() },
                    modifier = Modifier.padding(paddingValues)
                )
            }
            successMessage?.let {
                SuccessMessageScreen(
                    message = it,
                    onDismiss = {
                        userViewModel.clearSuccessMessage()
                        navController.navigate(Screens.USERS.title)
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            }
            if (isLoading) {
                LoadingScreen(modifier = Modifier.padding(paddingValues))
            }
        }
    )
}