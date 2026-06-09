package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy

@Composable
fun AstronomyFavoriteListContent(
    astronomyList: List<Astronomy>,
    onNavigateToDetail: (String) -> Unit,
    onRemove: (Astronomy) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item {
            Column {

                Text(
                    "Favoritos",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF032B75),
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Sua coleção pessoal de maravilhas cósmicas.",
                    color = Color(0xFF0B3D91)
                )
            }
        }
        items(astronomyList) { astronomy ->
            AstronomyFavoriteCard(
                astronomy = astronomy,
                onNavigateToDetail = { onNavigateToDetail(it) },
                onRemove = { onRemove(it) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AstronomyFavoriteListContentPreview() {
    MaterialTheme {
        AstronomyFavoriteListContent(
            astronomyList = listOf(
                Astronomy(
                    "01 JAN 2025",
                    "The closest star system to the Sun is the Alpha Centauri system. Of the three stars in the system, the dimmest -- called Proxima Centauri -- is actually the nearest star. The bright stars Alpha Centauri A and B form a close binary as they are separated by only 23 times the Earth- Sun distance - slightly greater than the distance between Uranus and the Sun. The Alphasystem is not visible in much of the northern hemisphere. Alpha Centauri A, also known as Rigil Kentaurus, is the brightest star in the constellation of Centaurus and is the fourth brightest star in the night sky. Sirius is the brightest even though it is more than twice as far away. By an exciting coincidence, Alpha Centauri A is the same type of star as our Sun, and Proxima Centauri is now known to have a potentially habitable exoplanet.",
                    "Alpha Centauri: The Closest Star System",
                    "https://apod.nasa.gov/apod/image/2501/AlphaCen_Cantrell_960.jpg",
                ),
                Astronomy(
                    "01 JAN 2025",
                    "The closest star system to the Sun is the Alpha Centauri system. Of the three stars in the system, the dimmest -- called Proxima Centauri -- is actually the nearest star. The bright stars Alpha Centauri A and B form a close binary as they are separated by only 23 times the Earth- Sun distance - slightly greater than the distance between Uranus and the Sun. The Alphasystem is not visible in much of the northern hemisphere. Alpha Centauri A, also known as Rigil Kentaurus, is the brightest star in the constellation of Centaurus and is the fourth brightest star in the night sky. Sirius is the brightest even though it is more than twice as far away. By an exciting coincidence, Alpha Centauri A is the same type of star as our Sun, and Proxima Centauri is now known to have a potentially habitable exoplanet.",
                    "Alpha Centauri: The Closest Star System",
                    "https://apod.nasa.gov/apod/image/2501/AlphaCen_Cantrell_960.jpg",
                ),
            ),
            onNavigateToDetail = {},
            onRemove = {}
        )
    }
}