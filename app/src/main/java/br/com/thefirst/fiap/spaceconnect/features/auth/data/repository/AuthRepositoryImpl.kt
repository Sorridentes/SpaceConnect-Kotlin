package br.com.thefirst.fiap.spaceconnect.features.auth.data.repository

import br.com.thefirst.fiap.spaceconnect.features.auth.data.model.toDomain
import br.com.thefirst.fiap.spaceconnect.features.auth.data.remote.FirebaseAuthDataSource
import br.com.thefirst.fiap.spaceconnect.common.Resource
import br.com.thefirst.fiap.spaceconnect.common.data.local.SessionManager
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.model.User
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val dataSource: FirebaseAuthDataSource,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun createUser(
        name: String,
        email: String,
        password: String
    ): Resource<User> {
        return try {
            val firebaseUser = dataSource.createUserWithEmailAndPassword(name, email, password)

            if (firebaseUser != null) {
                val user = firebaseUser.toDomain()

                sessionManager.saveUser(user.id, user.name, user.email)
                Resource.Success(user)
            } else {
                Resource.Error("Erro ao criar usuário")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro desconhecido ao criar usuário")
        }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): Resource<User> {
        return try {
            val firebaseUser = dataSource.signInWithEmailAndPassword(email, password)

            if (firebaseUser != null) {
                val user = firebaseUser.toDomain()
                sessionManager.saveUser(user.id, user.name, user.email)
                Resource.Success(user)
            } else {
                Resource.Error("Erro ao fazer login")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro desconhecido ao fazer login")
        }
    }

    override suspend fun signOut(): Resource<Unit> {
        return try {
            dataSource.signOut()
            sessionManager.cleanUser()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro desconhecido ao fazer logout")
        }
    }

    override fun getCurrentUser(): Flow<User?> {
        return sessionManager.getUser()
    }
}