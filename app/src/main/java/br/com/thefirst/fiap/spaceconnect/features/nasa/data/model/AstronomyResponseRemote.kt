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
    val date = this.formatDate()
    return Astronomy(
        date = date,
        description = explanation,
        title = title,
        image = url
    )
}

fun AstronomyResponse.formatDate(): String {
    try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)

        val date = LocalDate.parse(this.date, inputFormatter)
        return date.format(outputFormatter).uppercase()
    } catch (e: Exception) {
        return this.date
    }
}