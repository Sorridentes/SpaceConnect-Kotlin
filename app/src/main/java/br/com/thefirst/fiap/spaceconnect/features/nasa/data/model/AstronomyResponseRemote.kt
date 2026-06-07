package br.com.thefirst.fiap.spaceconnect.features.nasa.data.model

import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Serializable
data class AstronomyResponse(
    val date: String,
    val explanation: String,
    val title: String,
    val url: String
)

fun AstronomyResponse.toDomain(): Astronomy {
    return Astronomy(
        date = date,
        description = explanation,
        title = title,
        image = url
    )
}

fun AstronomyResponse.toEntity(): AstronomyEntity {
    return AstronomyEntity(
        date = this.date,
        description = this.explanation,
        title = this.title,
        image = this.url
    )
}