package br.com.thefirst.fiap.spaceconnect.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FirebaseAuthDataSourceImpl(
    private val auth: FirebaseAuth
) : FirebaseAuthDataSource {

    override suspend fun createUserWithEmailAndPassword(
        name: String,
        email: String,
        password: String
    ): FirebaseUser? {
        return suspendCancellableCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser

                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()

                        user?.updateProfile(profileUpdates)?.addOnCompleteListener { profileTask ->
                            continuation.resume(user)
                        } ?: continuation.resume(user)
                    } else {
                        continuation.resume(null)
                    }
                }
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): FirebaseUser? {
        return suspendCancellableCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(auth.currentUser)
                    } else {
                        continuation.resume(null)
                    }
                    }
                }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }


}