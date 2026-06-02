package br.com.thefirst.fiap.spaceconnect.features.firebase.domain.usecase

import br.com.thefirst.fiap.spaceconnect.common.Resource
import br.com.thefirst.fiap.spaceconnect.features.firebase.domain.repository.AuthRepository

class SignOutUseCase(
    private val repository: AuthRepository
){
    suspend operator fun invoke(): Resource<Unit> {

        return repository.signOut()
    }
}
