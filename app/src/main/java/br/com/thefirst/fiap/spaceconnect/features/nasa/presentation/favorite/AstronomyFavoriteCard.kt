package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.favorite

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun AstronomyFavoriteCard(
    astronomy: Astronomy,
    onNavigateToDetail: (String) -> Unit,
    onRemove: (Astronomy) -> Unit
) {

    val dateFormatted = try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
        LocalDate.parse(astronomy.date, inputFormatter)
            .format(outputFormatter).uppercase()
    } catch (e: Exception) {
        astronomy.date
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(width = 1.dp, Color(0xFFAAAAAA), CardDefaults.elevatedShape),
        onClick = { onNavigateToDetail(astronomy.date) }
    ) {

        Column {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(astronomy.image)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = dateFormatted,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF0B3D91),
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = astronomy.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    color = Color(0xFF0B3D91),
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Spacer(Modifier.weight(1f))
                    Button(
                        onClick = { onRemove(astronomy) },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color(0xFF8F0000)
                        )
                    ) {
                        Icon(Icons.Default.DeleteOutline, contentDescription = null)
                        Text("Remover")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AstronomyFavoriteCardPreview() {
    MaterialTheme {
        AstronomyFavoriteCard(
            astronomy = Astronomy(
                "01 JAN 2025",
                "The closest star system to the Sun is the Alpha Centauri system. Of the three stars in the system, the dimmest -- called Proxima Centauri -- is actually the nearest star. The bright stars Alpha Centauri A and B form a close binary as they are separated by only 23 times the Earth- Sun distance - slightly greater than the distance between Uranus and the Sun. The Alphasystem is not visible in much of the northern hemisphere. Alpha Centauri A, also known as Rigil Kentaurus, is the brightest star in the constellation of Centaurus and is the fourth brightest star in the night sky. Sirius is the brightest even though it is more than twice as far away. By an exciting coincidence, Alpha Centauri A is the same type of star as our Sun, and Proxima Centauri is now known to have a potentially habitable exoplanet.",
                "Alpha Centauri: The Closest Star System",
                "https://apod.nasa.gov/apod/image/2501/AlphaCen_Cantrell_960.jpg",
            ),
            onNavigateToDetail = {},
            onRemove = {},
        )
    }

}