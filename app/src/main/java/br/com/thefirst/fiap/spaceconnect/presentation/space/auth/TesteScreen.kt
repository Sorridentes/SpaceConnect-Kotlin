package br.com.thefirst.fiap.spaceconnect.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.thefirst.fiap.spaceconnect.domain.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    user: User? = null,
    onSignOut: () -> Unit,
    onNavigateToAuth: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Space Connect") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Bem-vindo ao Space Connect!",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (user != null) {
                Text(text = "Usuário: ${user.name}")
                Text(text = "Email: ${user.email}")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    onSignOut()
                    onNavigateToAuth()
                }
            ) {
                Text("Sair")
            }
        }
    }
}
