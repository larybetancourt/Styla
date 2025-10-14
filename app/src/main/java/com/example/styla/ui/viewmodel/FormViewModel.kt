package com.example.styla.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.styla.data.AuthRepository
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class FormUiState(
    val nombre: String = "",
    val correo: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val acceptedTerms: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

class FormViewModel(
    private val repo: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _ui = MutableStateFlow(FormUiState())
    val ui: StateFlow<FormUiState> = _ui

    fun onNombreChange(v: String) = _ui.update { it.copy(nombre = v, error = null) }
    fun onCorreoChange(v: String) = _ui.update { it.copy(correo = v, error = null) }
    fun onPasswordChange(v: String) = _ui.update { it.copy(password = v, error = null) }
    fun onConfirmPasswordChange(v: String) = _ui.update { it.copy(confirmPassword = v, error = null) }

    fun onTermsChange(checked: Boolean) = _ui.update { it.copy(acceptedTerms = checked, error = null) }

    fun consumeSuccess() = _ui.update { it.copy(success = false) }

    fun submit() {
        val s = _ui.value

        // Validaciones
        if (s.nombre.isBlank() || s.correo.isBlank() || s.password.isBlank() || s.confirmPassword.isBlank()) {
            _ui.update { it.copy(error = "Completa todos los campos") }; return
        }
        if (s.password != s.confirmPassword) {
            _ui.update { it.copy(error = "Las contraseñas no coinciden") }; return
        }
        if (s.password.length < 6) {
            _ui.update { it.copy(error = "La contraseña debe tener al menos 6 caracteres") }; return
        }
        if (!s.acceptedTerms) {
            _ui.update { it.copy(error = "Debes aceptar Términos y condiciones") }
            return
        }

        viewModelScope.launch {
            _ui.update { it.copy(loading = true, error = null, success = false) }
            try {
                repo.signUp(s.nombre.trim(), s.correo.trim(), s.password)
                _ui.update { it.copy(success = true) }
            } catch (e: Exception) {
                val msg = when (e) {
                    is FirebaseAuthUserCollisionException -> "Ya existe una cuenta con ese correo"
                    is FirebaseAuthWeakPasswordException -> "La contraseña es muy débil"
                    is FirebaseAuthException -> "[${e.errorCode}] ${e.message ?: "Error de autenticación"}"
                    is TimeoutCancellationException -> "Tiempo de espera con Firebase. Revisa tu red o Firestore."
                    is CancellationException -> "Operación cancelada"
                    else -> e.message ?: "Error creando la cuenta"
                }
                _ui.update { it.copy(error = msg, success = false) }
            } finally {
                // Pase lo que pase, apaga el loading ✔️
                _ui.update { it.copy(loading = false) }
            }
        }
    }
}

/** Helper para actualizar el StateFlow sin boilerplate */
private inline fun <T> MutableStateFlow<T>.update(block: (T) -> T) { value = block(value) }
