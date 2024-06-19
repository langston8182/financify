package com.financify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(private val authViewModel: AuthViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(authViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}