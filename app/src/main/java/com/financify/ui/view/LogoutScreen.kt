package com.financify.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.financify.model.Screens
import com.financify.viewmodel.AuthViewModel

@Composable
fun LogoutScreen(authViewModel: AuthViewModel, navController: NavController) {
    LaunchedEffect(Unit) {
        authViewModel.logout()
        navController.navigate(Screens.WELCOME.title) {
            popUpTo(Screens.WELCOME.title) { inclusive = true }
        }
    }
}