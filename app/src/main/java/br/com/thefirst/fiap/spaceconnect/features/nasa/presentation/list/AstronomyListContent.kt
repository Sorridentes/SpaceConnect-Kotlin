package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy

@Composable
fun AstronomyListContent(
    astronomyList: List<Astronomy>,
    onNavigateToDetail: (Astronomy) -> Unit,
    onFavorite: (Astronomy) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(astronomyList) { astronomy ->
            AstronomyCard(
                astronomy = astronomy,
                onNavigateToDetail = { onNavigateToDetail(it) },
                onFavorite = { onFavorite(it) }
            )
        }
    }
}

