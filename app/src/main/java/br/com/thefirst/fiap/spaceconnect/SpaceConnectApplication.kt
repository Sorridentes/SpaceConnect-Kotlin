package br.com.thefirst.fiap.spaceconnect

import android.app.Application
import br.com.thefirst.fiap.spaceconnect.features.firebase.di.firebaseModuleInclude
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SpaceConnectApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@SpaceConnectApplication)
            modules(
                firebaseModuleInclude,
            )
        }
    }
}