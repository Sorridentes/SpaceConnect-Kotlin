package br.com.thefirst.fiap.spaceconnect.features.nasa.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy

@Entity(tableName = "astronomy_table")
data class AstronomyEntity(
    @PrimaryKey
    val date: String,
    val description: String,
    val title: String,
    val image: String,
    val favorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

fun AstronomyEntity.toDomain(): Astronomy {
    return Astronomy(
        date = date,
        description = description,
        title = title,
        image = image,
        favorite = favorite
    )
}

fun Astronomy.toEntity(): AstronomyEntity {
    return AstronomyEntity(
        date = date,
        description = description,
        title = title,
        image = image,
        favorite = favorite
    )
}