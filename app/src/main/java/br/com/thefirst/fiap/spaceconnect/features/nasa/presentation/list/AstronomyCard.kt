package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.list

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import br.com.thefirst.fiap.spaceconnect.ui.theme.SpaceConnectTheme
import coil3.compose.AsyncImage
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun AstronomyCard(
    astronomy: Astronomy,
    onNavigateToDetail: (String) -> Unit,
    onFavorite: (astronomy: Astronomy) -> Unit
) {
    var dateFormatted = ""
    try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)

        dateFormatted = LocalDate.parse(astronomy.date, inputFormatter).format(outputFormatter).uppercase()

    } catch (e: Exception) {
        dateFormatted = astronomy.date
    }

    ElevatedCard(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(width = 1.dp, Color(0xFFAAAAAA), CardDefaults.elevatedShape),
        onClick = {onNavigateToDetail(astronomy.date) },
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
    ) {

        Row(modifier = Modifier.padding(12.dp)) {

            AsyncImage(
                model = astronomy.image,
                contentDescription = null,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(12.dp))

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = dateFormatted,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF0B3D91),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = { onFavorite(astronomy) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = if (astronomy.favorite) Icons.Default.Star else Icons.Default.StarBorder,
                            contentDescription = null,
                            tint = if (astronomy.favorite) Color(0xFF631FD2) else Color.Gray
                        )
                    }
                }

                Text(
                    text = astronomy.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    color = Color(0xFF0B3D91),
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Preview
@Composable
private fun AstronomyCardPreview() {
    SpaceConnectTheme {
        AstronomyCard(
            onNavigateToDetail = {},
            astronomy = Astronomy(
                "01 JAN 2025",
                "The closest star system to the Sun is the Alpha Centauri system. Of the three stars in the system, the dimmest -- called Proxima Centauri -- is actually the nearest star. The bright stars Alpha Centauri A and B form a close binary as they are separated by only 23 times the Earth- Sun distance - slightly greater than the distance between Uranus and the Sun. The Alphasystem is not visible in much of the northern hemisphere. Alpha Centauri A, also known as Rigil Kentaurus, is the brightest star in the constellation of Centaurus and is the fourth brightest star in the night sky. Sirius is the brightest even though it is more than twice as far away. By an exciting coincidence, Alpha Centauri A is the same type of star as our Sun, and Proxima Centauri is now known to have a potentially habitable exoplanet.",
                "Alpha Centauri: The Closest Star System",
                "https://apod.nasa.gov/apod/image/2501/AlphaCen_Cantrell_960.jpg",
                true
            ),
            onFavorite = { }
        )

    }
}