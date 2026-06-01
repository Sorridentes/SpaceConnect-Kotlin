package br.com.thefirst.fiap.spaceconnect.data.repository

import br.com.thefirst.fiap.spaceconnect.data.model.toDomain
import br.com.thefirst.fiap.spaceconnect.data.remote.FirebaseAuthDataSource
import br.com.thefirst.fiap.spaceconnect.domain.common.Resource
import br.com.thefirst.fiap.spaceconnect.domain.model.User
import br.com.thefirst.fiap.spaceconnect.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val dataSource: FirebaseAuthDataSource
) : AuthRepository {
    override suspend fun createUser(
        name: String,
        email: String,
        password: String
    ): Resource<User> {
        return try {
            val firebaseUser = dataSource.createUserWithEmailAndPassword(name, email, password)

            if (firebaseUser != null) {
                Resource.Success(firebaseUser.toDomain())
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
                Resource.Success(firebaseUser.toDomain())
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
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro desconhecido ao fazer logout")
        }
    }
}