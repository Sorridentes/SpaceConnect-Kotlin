package br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Astronomy (
    val date: String,
    val description: String,
    val title: String,
    val image: String,
    val favorite: Boolean = false
)