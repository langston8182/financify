package com.financify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financify.model.User
import com.financify.service.UserApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersViewModel(authViewModel: AuthViewModel) : BaseViewModel() {
    private var token = authViewModel.token
    private val _users = MutableStateFlow<List<User>?>(null)
    var users: StateFlow<List<User>?> = _users.asStateFlow()

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val fetchedUsers = UserApi.api(token.value ?: "").getUsers()
                println("Users $fetchedUsers")
                _users.value = fetchedUsers
            } catch (ex: Exception) {
                setErrorMessage(ex.message)
            }
        }
    }
}