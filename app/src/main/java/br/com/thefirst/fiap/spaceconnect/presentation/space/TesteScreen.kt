package br.com.thefirst.fiap.spaceconnect.presentation.space

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import br.com.thefirst.fiap.spaceconnect.features.nasa.graphic.list.TestNasaScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userName: String,
    onSearchClick: (startDate: String, endDate: String) -> Unit,
    onSignOut: () -> Unit
) {
    TestNasaScreen(
        onSearchClick = { startDate, endDate -> onSearchClick(startDate, endDate) },
        onSingOut = onSignOut
    )
}
