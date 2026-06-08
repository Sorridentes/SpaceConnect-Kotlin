package br.com.thefirst.fiap.spaceconnect.features.auth.domain.repository

import br.com.thefirst.fiap.spaceconnect.common.Resource
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(name: String, email: String, password: String): Resource<User>
    suspend fun signIn(email: String, password: String): Resource<User>
    suspend fun signOut(): Resource<Unit>
    fun getCurrentUser(): Flow<User?>

}