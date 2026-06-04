package br.com.thefirst.fiap.spaceconnect.features.auth.di

import org.koin.dsl.module

val authModuleInclude = module {
    includes(
        dataModule,
        domainModule,
        graphicModule
    )
}