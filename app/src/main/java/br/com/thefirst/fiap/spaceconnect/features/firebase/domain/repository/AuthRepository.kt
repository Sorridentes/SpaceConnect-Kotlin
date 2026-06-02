package br.com.thefirst.fiap.spaceconnect.features.firebase.domain.repository

import br.com.thefirst.fiap.spaceconnect.features.firebase.domain.common.Resource
import br.com.thefirst.fiap.spaceconnect.features.firebase.domain.model.User

interface AuthRepository {
    suspend fun createUser(name: String, email: String, password: String): Resource<User>
    suspend fun signIn(email: String, password: String): Resource<User>
    suspend fun signOut(): Resource<Unit>

}