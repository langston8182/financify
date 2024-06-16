package com.financify.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseViewModel : ViewModel() {
    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    protected fun setSuccessMessage(message: String?) {
        _successMessage.value = message
    }

    protected fun clearMessageSuccess() {
        _successMessage.value = null
    }

    protected fun setErrorMessage(message: String?) {
        _errorMessage.value = message
    }

    protected fun clearMessageError() {
        _errorMessage.value = null
    }

    protected fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}