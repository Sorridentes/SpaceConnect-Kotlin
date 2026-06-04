package br.com.thefirst.fiap.spaceconnect.features.auth.di

import br.com.thefirst.fiap.spaceconnect.features.auth.domain.usecase.CreateUserUseCase
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.usecase.SignInUseCase
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.usecase.SignOutUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { CreateUserUseCase(repository = get()) }
    factory { SignInUseCase(repository = get()) }
    factory { SignOutUseCase(repository = get()) }
}