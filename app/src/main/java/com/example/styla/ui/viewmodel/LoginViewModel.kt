package com.example.styla.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.styla.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

class LoginViewModel(
    private val repo: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _ui = MutableStateFlow(LoginUiState())
    val ui: StateFlow<LoginUiState> = _ui

    fun onEmailChange(v: String) = _ui.update { it.copy(email = v, error = null) }
    fun onPasswordChange(v: String) = _ui.update { it.copy(password = v, error = null) }

    fun login() {
        val s = _ui.value
        if (s.email.isBlank() || s.password.isBlank()) {
            _ui.update { it.copy(error = "Completa email y contraseña") }
            return
        }
        viewModelScope.launch {
            _ui.update { it.copy(loading = true, error = null) }
            try {
                repo.signInWithEmail(s.email.trim(), s.password)
                _ui.update { it.copy(loading = false, success = true) }
            } catch (e: Exception) {
                _ui.update { it.copy(loading = false, error = e.message ?: "Error iniciando sesión") }
            }
        }
    }
}

private inline fun <T> MutableStateFlow<T>.update(block: (T) -> T) { value = block(value) }
