// AstronomySplashScreen.kt (atualizado)
package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.splash

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppShortcut
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import br.com.thefirst.fiap.spaceconnect.R
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import kotlinx.coroutines.delay
import kotlin.random.Random
import org.koin.androidx.compose.koinViewModel

@Composable
fun AstronomySplashScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: AstronomySplashViewModel = koinViewModel()
) {
    val navigationTarget by viewModel.navigationTarget.collectAsState()

    // Navega quando o destino é definido
    LaunchedEffect(navigationTarget) {
        delay(2000)
        when (navigationTarget) {
            AstronomySplashViewModel.NavigationTarget.Onboarding -> onNavigateToOnboarding()
            AstronomySplashViewModel.NavigationTarget.Auth -> onNavigateToAuth()
            AstronomySplashViewModel.NavigationTarget.Home -> onNavigateToHome()
            null -> { /* Aguardando */ }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.tela_fundo)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color(0x88FFFFFF),
                        shape = CircleShape
                    )
                    .padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AppShortcut,
                    contentDescription = null,
                    tint = Color(0x88FFFFFF),
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "CosmoView",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "OBSERVING",
                    color = Color.White.copy(0.5f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = "THE",
                    color = Color.White.copy(0.5f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = "INFINITE",
                    color = Color.White.copy(0.5f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.5.sp
                )
            }

            Spacer(Modifier.height(100.dp))

            CircularProgressIndicator(
                color = Color.White
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Verificando configurações...",
                color = Color.White.copy(0.7f)
            )
        }
    }
}