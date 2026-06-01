package br.com.thefirst.fiap.spaceconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import br.com.thefirst.fiap.spaceconnect.presentation.space.auth.AuthenticationScreen
import br.com.thefirst.fiap.spaceconnect.ui.theme.SpaceConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            SpaceConnectTheme {
                AuthenticationScreen()
            }
        }
    }
}
