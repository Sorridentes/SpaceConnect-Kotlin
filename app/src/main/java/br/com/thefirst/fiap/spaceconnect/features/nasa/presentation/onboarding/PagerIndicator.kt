package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PagerIndicator(
    currentPage: Int,
    totalPages: Int
) {

    Row(
        horizontalArrangement = Arrangement.Center
    ) {

        repeat(totalPages) { index ->

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .width(
                        if (currentPage == index) 24.dp
                        else 8.dp
                    )
                    .height(8.dp)
                    .clip(CircleShape)
                    .background(
                        if (currentPage == index)
                            Color(0xFF083B8E)
                        else
                            Color.LightGray
                    )
            )
        }
    }
}