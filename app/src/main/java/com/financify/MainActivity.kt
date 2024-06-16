package com.financify

import AuthViewModelFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.financify.ui.theme.FinancifyTheme
import com.financify.ui.view.Drawer
import com.financify.viewmodel.AuthViewModel
import com.financify.viewmodel.UsersViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModelFactory = AuthViewModelFactory(applicationContext)
        enableEdgeToEdge()
        setContent {
            val authViewModel: AuthViewModel = viewModel(factory = authViewModelFactory)
            val usersViewModelFactory = UsersViewModelFactory(authViewModel)
            FinancifyTheme {
                val manager = LocalFocusManager.current
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { manager.clearFocus() }
                ) {
                    Drawer(
                        authViewModelFactory = authViewModelFactory,
                        usersViewModelFactory = usersViewModelFactory
                    )
                }
            }
        }
    }
}