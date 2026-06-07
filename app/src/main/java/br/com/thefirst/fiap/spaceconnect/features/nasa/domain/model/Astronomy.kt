package br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model

import br.com.thefirst.fiap.spaceconnect.features.nasa.data.model.AstronomyEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


data class Astronomy(
    val date: String,
    val description: String,
    val title: String,
    val image: String,
    val favorite: Boolean = false
)

fun Astronomy.toEntity(): AstronomyEntity {
    return AstronomyEntity(
        date = date,
        description = description,
        title = title,
        image = image,
        favorite = favorite
    )
}