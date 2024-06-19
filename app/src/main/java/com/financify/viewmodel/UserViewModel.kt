package com.financify.viewmodel

import androidx.lifecycle.viewModelScope
import com.financify.model.User
import com.financify.service.UserApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(authViewModel: AuthViewModel) : BaseViewModel(){
    private var token = authViewModel.token
    private val _selectedUser = MutableStateFlow<User?>(null);
    val selectedUser: StateFlow<User?> = _selectedUser.asStateFlow()

    fun selectUser(user: User) {
        _selectedUser.value = user
    }

    fun updateGivenName(givenName: String) {
        _selectedUser.value = _selectedUser.value?.copy(givenName = givenName)
    }

    fun updateFamilyName(familyName: String) {
        _selectedUser.value = _selectedUser.value?.copy(familyName = familyName)
    }

    fun updateUser() {
        viewModelScope.launch {
            setLoading(true)
            try {
                UserApi.api(token.value ?: "").updateUser(_selectedUser.value?.id!!, _selectedUser.value!!)
                setSuccessMessage("Utilisateur modifié avec succès")
                setLoading(false)
            } catch (ex: Exception) {
                setLoading(false)
                setErrorMessage(ex.message)
            }
            setLoading(false)
        }
    }
}