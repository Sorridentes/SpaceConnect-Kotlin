package br.com.thefirst.fiap.spaceconnect.features.auth.di


import br.com.thefirst.fiap.spaceconnect.features.auth.presentation.auth.AuthenticationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val graphicModule = module {
    viewModelOf(::AuthenticationViewModel)
}