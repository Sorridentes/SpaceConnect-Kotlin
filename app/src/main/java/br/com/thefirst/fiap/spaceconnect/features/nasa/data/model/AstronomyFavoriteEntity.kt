package br.com.thefirst.fiap.spaceconnect.features.nasa.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy

@Entity(tableName = "favorite_astronomy_table")
data class AstronomyFavoriteEntity(
    @PrimaryKey
    val date: String,
    val description: String,
    val title: String,
    val image: String,
    val favorite: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

fun Astronomy.toFavoriteEntity(): AstronomyFavoriteEntity {
    return AstronomyFavoriteEntity(
        date = date,
        description = description,
        title = title,
        image = image,
        favorite = true
    )
}

fun AstronomyFavoriteEntity.toDomain(): Astronomy {
    return Astronomy(
        date = date,
        description = description,
        title = title,
        image = image,
        favorite = true
    )
}