package br.com.thefirst.fiap.spaceconnect.features.firebase.di

import org.koin.dsl.module

val firebaseModuleInclude = module {
    includes(
        firebaseModule,
        dataModule,
        domainModule,
        graphicModule
    )
}