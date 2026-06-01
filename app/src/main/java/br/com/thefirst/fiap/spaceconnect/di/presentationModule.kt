package br.com.thefirst.fiap.spaceconnect.di


import br.com.thefirst.fiap.spaceconnect.presentation.space.auth.AuthenticationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentation = module {
    viewModelOf(::AuthenticationViewModel)
}