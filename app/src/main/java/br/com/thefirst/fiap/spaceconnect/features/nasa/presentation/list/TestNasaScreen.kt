package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import br.com.thefirst.fiap.spaceconnect.ui.theme.SpaceConnectTheme

@Composable
fun TestNasaScreen(
    onSearchClick: (startDate: String, endDate: String) -> Unit,
    onSingOut: () -> Unit
) {
    var startDate by remember { mutableStateOf("2024-01-01") }
    var endDate by remember { mutableStateOf("2024-01-03") }
    var isLoading by remember { mutableStateOf(false) }
    var resultText by remember { mutableStateOf("") }
    var astronomyList by remember { mutableStateOf<List<Astronomy>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            Text(
                text = "🌌 NASA APOD API Test",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        item {
            OutlinedTextField(
                value = startDate,
                onValueChange = { startDate = it },
                label = { Text("Data Inicial (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth(),
                isError = startDate.isNotEmpty() && !isValidDate(startDate)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            OutlinedTextField(
                value = endDate,
                onValueChange = { endDate = it },
                label = { Text("Data Final (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth(),
                isError = endDate.isNotEmpty() && !isValidDate(endDate)
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            Button(
                onClick = { onSearchClick(startDate, endDate) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && startDate.isNotBlank() && endDate.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(if (isLoading) "Buscando..." else "Buscar Astronomia")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        errorMessage?.let { error ->
            item {
                ErrorMessage(error)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        if (astronomyList.isNotEmpty()) {
            item {
                Text(
                    text = "📡 RESULTADOS (${astronomyList.size})",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
            items(astronomyList) { astronomy ->
                AstronomyCard(astronomy = astronomy)
                Spacer(modifier = Modifier.height(12.dp))
            }
        } else if (resultText.isNotEmpty() && !isLoading) {
            item {
                Text(resultText, style = MaterialTheme.typography.bodyMedium)
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onSingOut
            ) {
                Text("Sair")
            }
        }
    }

}

@Composable
fun AstronomyCard(astronomy: Astronomy) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = astronomy.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = "📅 Data: ${astronomy.date}", style = MaterialTheme.typography.bodySmall)
            Text(
                text = astronomy.description.take(150) + "...",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ErrorMessage(message: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "❌ ERRO: $message",
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onErrorContainer
        )
    }
}

private fun isValidDate(date: String): Boolean {
    return Regex("\\d{4}-\\d{2}-\\d{2}").matches(date)
}

@Preview(showBackground = true)
@Composable
private fun TestNasaScreenPreview() {
    SpaceConnectTheme {
        TestNasaScreen(onSearchClick = { _, _ -> }, onSingOut = {})
    }

}
