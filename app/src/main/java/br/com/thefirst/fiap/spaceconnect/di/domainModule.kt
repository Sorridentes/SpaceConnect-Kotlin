package br.com.thefirst.fiap.spaceconnect.di

import br.com.thefirst.fiap.spaceconnect.domain.usecase.CreateUserUseCase
import br.com.thefirst.fiap.spaceconnect.domain.usecase.SignInUseCase
import br.com.thefirst.fiap.spaceconnect.domain.usecase.SignOutUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { CreateUserUseCase(repository = get()) }
    factory { SignInUseCase(repository = get()) }
    factory { SignOutUseCase(repository = get()) }
}