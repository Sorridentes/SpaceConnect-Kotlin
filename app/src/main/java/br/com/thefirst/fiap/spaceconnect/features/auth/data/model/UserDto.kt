package br.com.thefirst.fiap.spaceconnect.features.auth.data.model

import br.com.thefirst.fiap.spaceconnect.features.auth.domain.model.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toDomain(): User {
    return User(
        id = uid,
        name = displayName ?: "",
        email = email ?: ""
    )
}