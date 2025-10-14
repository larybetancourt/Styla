package com.example.styla.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    suspend fun signUp(name: String, email: String, password: String) =
        withContext(Dispatchers.IO) {

            //Crear usuario
            val result = withTimeout(15_000) {
                auth.createUserWithEmailAndPassword(email, password).await()
            }
            val uid = result.user?.uid ?: error("No UID returned")

            //Guardar perfil en Firestore
            val userDoc = mapOf(
                "name" to name,
                "email" to email,
                "createdAt" to FieldValue.serverTimestamp()
            )
            withTimeout(15_000) {
                db.collection("users").document(uid)
                    .set(userDoc, SetOptions.merge())
                    .await()
            }
        }

    suspend fun signInWithEmail(email: String, password: String) =
        withContext(Dispatchers.IO) {
            withTimeout(15_000) {
                auth.signInWithEmailAndPassword(email, password).await()
            }
        }

    fun signOut() = auth.signOut()

    suspend fun updateProfile(gender: String?, birthdayDdMmYy: String?) {
        val uid = auth.currentUser?.uid ?: error("No user logged")
        val data = buildMap<String, Any> {
            gender?.takeIf { it.isNotBlank() }?.let { put("gender", it) }
            birthdayDdMmYy?.takeIf { it.isNotBlank() }?.let { put("birthday", it) } // ej: 14/10/25
            put("updatedAt", FieldValue.serverTimestamp())
        }
        db.collection("users").document(uid).set(data, SetOptions.merge()).await()
    }

}
