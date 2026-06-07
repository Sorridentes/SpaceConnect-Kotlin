package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.List,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = Color(0xFF0B3D91)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Nenhuma tarefa ainda",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF0B3D91)
        )
        Text(
            text = "Adicione sua primeira tarefa acima",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF3E6198)
        )
    }


}