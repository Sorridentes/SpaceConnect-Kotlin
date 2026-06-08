package br.com.thefirst.fiap.spaceconnect.features.auth.domain.usecase

import br.com.thefirst.fiap.spaceconnect.common.Resource
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.model.User
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.repository.AuthRepository

class SignUpUseCase (
    private val repository: AuthRepository
) {
    suspend operator fun invoke(name: String, email: String, password: String): Resource<User> {
        if (name.isBlank() || email.isBlank()){
            return Resource.Error("Preencha todos os campos")
        }

        if (password.length < 6){
            return Resource.Error("A senha deve ter no mínimo 6 caracteres")
        }

        return repository.signUp(name, email, password)
    }
}
