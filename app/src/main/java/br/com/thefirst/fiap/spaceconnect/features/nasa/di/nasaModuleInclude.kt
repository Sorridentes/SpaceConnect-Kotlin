package br.com.thefirst.fiap.spaceconnect.features.nasa.di

import org.koin.dsl.module

val nasaModuleInclude = module {
    includes(
        networkModule,
        dataModule,
        domainModule,
        presentationModule
    )
}