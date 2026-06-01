package br.com.thefirst.fiap.spaceconnect.domain.usecase

import br.com.thefirst.fiap.spaceconnect.domain.common.Resource
import br.com.thefirst.fiap.spaceconnect.domain.repository.AuthRepository

class SignOutUseCase(
    private val repository: AuthRepository
){
    suspend operator fun invoke(): Resource<Unit> {

        return repository.signOut()
    }
}
