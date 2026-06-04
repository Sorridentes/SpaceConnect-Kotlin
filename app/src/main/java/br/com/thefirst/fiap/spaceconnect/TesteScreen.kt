package br.com.thefirst.fiap.spaceconnect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.model.User

@Composable
fun HomeScreen(
    user: User?,
    onSignOut: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Bem-vindo, ${user?.name ?: "Usuário"}!")
        Text("Email: ${user?.email ?: ""}")

        Button(
            onClick = onSignOut,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Sair")
        }
    }
}