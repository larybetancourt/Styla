package com.example.styla.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class AboutYouUiState(
    val gender: String = "",
    val birthday: String = "",
    val style: String = "",
    val loading: Boolean = false,
    val saved: Boolean = false,
    val error: String? = null
)

class AboutYouViewModel(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ViewModel() {

    private val _ui = MutableStateFlow(AboutYouUiState())
    val ui: StateFlow<AboutYouUiState> = _ui.asStateFlow()

    fun onGenderChange(v: String) = _ui.update { it.copy(gender = v, saved = false) }
    fun onBirthdayChange(v: String) = _ui.update { it.copy(birthday = v, saved = false) }
    fun onStyleChange(v: String) = _ui.update { it.copy(style = v, saved = false) } // ⬅️ nuevo

    fun save() {
        val s = _ui.value
        val uid = auth.currentUser?.uid ?: run {
            _ui.update { it.copy(error = "Usuario no autenticado") }
            return
        }

        viewModelScope.launch {
            _ui.update { it.copy(loading = true, error = null, saved = false) }
            try {
                val data = hashMapOf(
                    "gender" to s.gender,
                    "birthday" to s.birthday,
                    "style" to s.style,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
                db.collection("users").document(uid)
                    .set(data, SetOptions.merge())
                    .await()

                _ui.update { it.copy(loading = false, saved = true) }
            } catch (e: Exception) {
                _ui.update { it.copy(loading = false, error = e.message ?: "Error guardando datos") }
            }
        }
    }
}
