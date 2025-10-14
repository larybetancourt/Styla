package com.example.styla.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class StartUiState(
    val fullName: String = "",
    val loading: Boolean = false
) {
    val firstName: String
        get() = fullName.trim().substringBefore(" ").ifEmpty { "!" }
}

class StartViewModel(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ViewModel() {

    private val _ui = MutableStateFlow(StartUiState(loading = true))
    val ui: StateFlow<StartUiState> = _ui.asStateFlow()

    init {
        loadName()
    }

    private fun loadName() = viewModelScope.launch {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            _ui.update { it.copy(loading = false, fullName = "") }
            return@launch
        }
        try {
            val snap = db.collection("users").document(uid).get().await()
            val name = snap.getString("name").orEmpty()
            _ui.update { it.copy(fullName = name, loading = false) }
        } catch (_: Exception) {
            _ui.update { it.copy(loading = false) } // si falla, solo no mostramos el nombre
        }
    }
}
