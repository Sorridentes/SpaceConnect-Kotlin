package br.com.thefirst.fiap.spaceconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import br.com.thefirst.fiap.spaceconnect.navigation.AppNavigation
import br.com.thefirst.fiap.spaceconnect.ui.theme.SpaceConnectTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            SpaceConnectTheme {
                KoinContext {
                    AppNavigation()
                }
            }
        }
    }
}
