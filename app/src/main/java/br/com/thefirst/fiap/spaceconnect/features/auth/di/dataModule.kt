package br.com.thefirst.fiap.spaceconnect.features.auth.di

import br.com.thefirst.fiap.spaceconnect.features.auth.data.remote.FirebaseAuthDataSource
import br.com.thefirst.fiap.spaceconnect.features.auth.data.remote.FirebaseAuthDataSourceImpl
import br.com.thefirst.fiap.spaceconnect.features.auth.data.repository.AuthRepositoryImpl
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.repository.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import org.koin.dsl.module

val dataModule = module {
    single<FirebaseAuthDataSource> {
        FirebaseAuthDataSourceImpl(auth = get())
    }

    single<AuthRepository> {
        AuthRepositoryImpl(dataSource = get())
    }

    single { Firebase.auth }
}