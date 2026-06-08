package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.list.AstronomyCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstronomyFavoritesScreen(
    onSignOut: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToList: () -> Unit,
    viewModel: AstronomyFavoritesViewModel = koinViewModel()
) {
    val favoritesList by viewModel.favoritesList.collectAsState()
    var selectedTab by remember { mutableIntStateOf(1) }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "APOD 2025",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onSignOut) {
                        Icon(
                            Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Sair"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF0B3D91),
                    navigationIconContentColor = Color(0xFF0B3D91),
                    actionIconContentColor = Color(0xFF0B3D91)
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = onNavigateToList,
                    icon = {
                        Icon(Icons.Default.BookmarkBorder, null)
                    },
                    label = { Text("Galeria") }
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab == 1 },
                    icon = {
                        Icon(Icons.Default.Star, null)
                    },
                    label = { Text("Favoritos") }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (favoritesList.isEmpty()) {
                EmptyState()
            } else {
                AstronomyFavoriteListContent(
                    astronomyList = favoritesList,
                    onNavigateToDetail = { onNavigateToDetail(it) },
                    onRemove = { viewModel.removeFavorite(it) }
                )
            }
        }
    }
}