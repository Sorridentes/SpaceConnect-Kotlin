package br.com.thefirst.fiap.spaceconnect.features.firebase.di

import br.com.thefirst.fiap.spaceconnect.features.firebase.domain.usecase.CreateUserUseCase
import br.com.thefirst.fiap.spaceconnect.features.firebase.domain.usecase.SignInUseCase
import br.com.thefirst.fiap.spaceconnect.features.firebase.domain.usecase.SignOutUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { CreateUserUseCase(repository = get()) }
    factory { SignInUseCase(repository = get()) }
    factory { SignOutUseCase(repository = get()) }
}