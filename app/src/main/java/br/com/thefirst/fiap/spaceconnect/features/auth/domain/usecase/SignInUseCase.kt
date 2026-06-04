package br.com.thefirst.fiap.spaceconnect.features.auth.domain.usecase

import br.com.thefirst.fiap.spaceconnect.common.Resource
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.model.User
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.repository.AuthRepository

class SignInUseCase (
    private val repository: AuthRepository
){
    suspend operator fun invoke(email: String, password: String): Resource<User> {
        if (email.isBlank() || password.isBlank()){
            return Resource.Error("Preencha todos os campos")
        }

        return repository.signIn(email, password)
    }
}
