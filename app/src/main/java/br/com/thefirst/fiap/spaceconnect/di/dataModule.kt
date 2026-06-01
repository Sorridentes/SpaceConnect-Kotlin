package br.com.thefirst.fiap.spaceconnect.di

import br.com.thefirst.fiap.spaceconnect.data.remote.FirebaseAuthDataSource
import br.com.thefirst.fiap.spaceconnect.data.remote.FirebaseAuthDataSourceImpl
import br.com.thefirst.fiap.spaceconnect.data.repository.AuthRepositoryImpl
import br.com.thefirst.fiap.spaceconnect.domain.repository.AuthRepository
import org.koin.dsl.module

val dataModule = module {
    single<FirebaseAuthDataSource> {
        FirebaseAuthDataSourceImpl(auth = get())
    }

    single<AuthRepository> {
        AuthRepositoryImpl(dataSource = get())
    }
}