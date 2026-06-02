package br.com.thefirst.fiap.spaceconnect.features.firebase.data.model

import br.com.thefirst.fiap.spaceconnect.features.firebase.domain.model.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toDomain(): User {
    return User(
        id = uid,
        name = displayName ?: "",
        email = email ?: ""
    )
}