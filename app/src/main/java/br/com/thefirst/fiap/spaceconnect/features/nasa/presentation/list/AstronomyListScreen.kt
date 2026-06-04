package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import br.com.thefirst.fiap.spaceconnect.ui.theme.SpaceConnectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstronomyListScreen(
    astronomyList: List<Astronomy>,
    onNavigateToDetail: (Astronomy) -> Unit,
    onNavigateToFavorite: () -> Unit,
    onFavorite: (astronomy: Astronomy) -> Unit,
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("APOD 2025")
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(Icons.Default.PhotoLibrary, null)
                    },
                    label = {
                        Text("Galeria")
                    }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onNavigateToFavorite,
                    icon = {
                        Icon(Icons.Default.StarBorder, null)
                    },
                    label = {
                        Text("Favoritos")
                    }
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(astronomyList) { astronomy ->

                    AstronomyCard(
                        astronomy = astronomy,
                        onNavigateToDetail = { onNavigateToDetail(it) },
                        onFavorite = { onFavorite(it) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AstronomyListScreenPreview() {
    SpaceConnectTheme {
        AstronomyListScreen(astronomyList = listOf(Astronomy(
            "01 JAN 2025",
            "The closest star system to the Sun is the Alpha Centauri system. Of the three stars in the system, the dimmest -- called Proxima Centauri -- is actually the nearest star. The bright stars Alpha Centauri A and B form a close binary as they are separated by only 23 times the Earth- Sun distance - slightly greater than the distance between Uranus and the Sun. The Alphasystem is not visible in much of the northern hemisphere. Alpha Centauri A, also known as Rigil Kentaurus, is the brightest star in the constellation of Centaurus and is the fourth brightest star in the night sky. Sirius is the brightest even though it is more than twice as far away. By an exciting coincidence, Alpha Centauri A is the same type of star as our Sun, and Proxima Centauri is now known to have a potentially habitable exoplanet.",
            "Alpha Centauri: The Closest Star System",
            "https://apod.nasa.gov/apod/image/2501/AlphaCen_Cantrell_960.jpg",
            true
        ),Astronomy(
            "01 JAN 2025",
            "The closest star system to the Sun is the Alpha Centauri system. Of the three stars in the system, the dimmest -- called Proxima Centauri -- is actually the nearest star. The bright stars Alpha Centauri A and B form a close binary as they are separated by only 23 times the Earth- Sun distance - slightly greater than the distance between Uranus and the Sun. The Alphasystem is not visible in much of the northern hemisphere. Alpha Centauri A, also known as Rigil Kentaurus, is the brightest star in the constellation of Centaurus and is the fourth brightest star in the night sky. Sirius is the brightest even though it is more than twice as far away. By an exciting coincidence, Alpha Centauri A is the same type of star as our Sun, and Proxima Centauri is now known to have a potentially habitable exoplanet.",
            "Alpha Centauri: The Closest Star System",
            "https://apod.nasa.gov/apod/image/2501/AlphaCen_Cantrell_960.jpg",
        ),),
            onNavigateToDetail = {_ -> },
            onNavigateToFavorite = {},
            onFavorite = {_ ->})
    }
}