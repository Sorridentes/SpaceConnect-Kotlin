package br.com.thefirst.fiap.spaceconnect.data.remote

import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthDataSource {
    suspend fun createUserWithEmailAndPassword(
        name: String,
        email: String,
        password: String
    ): FirebaseUser?

    suspend fun signInWithEmailAndPassword(email: String, password: String): FirebaseUser?

    suspend fun signOut()

    fun getCurrentUser(): FirebaseUser?
}