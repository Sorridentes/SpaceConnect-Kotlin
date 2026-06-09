package br.com.thefirst.fiap.spaceconnect.features.auth.presentation.auth

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppShortcut
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Logo() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0x880B3D91),
                    shape = CircleShape
                )
                .padding(20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AppShortcut,
                contentDescription = null,
                tint = Color(0xFF0B3D91),
                modifier = Modifier.size(50.dp)
            )
        }
        Text(
            "Space Connect", color = Color(0xFF0B3D91),
            fontSize = 30.sp
        )
    }
}

