package br.com.thefirst.fiap.spaceconnect

import android.app.Application
import android.graphics.Bitmap
import br.com.thefirst.fiap.spaceconnect.features.auth.di.authModuleInclude
import br.com.thefirst.fiap.spaceconnect.features.nasa.di.nasaModuleInclude
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.request.bitmapConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SpaceConnectApplication : Application(), SingletonImageLoader.Factory {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@SpaceConnectApplication)
            modules(
                authModuleInclude,
                nasaModuleInclude
            )
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader.Builder(context)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build()
    }
}