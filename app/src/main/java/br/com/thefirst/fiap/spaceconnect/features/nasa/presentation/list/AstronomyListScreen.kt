package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.thefirst.fiap.spaceconnect.common.UiState
import br.com.thefirst.fiap.spaceconnect.features.auth.domain.model.User
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.Astronomy
import br.com.thefirst.fiap.spaceconnect.ui.theme.SpaceConnectTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstronomyListScreen(
    user: User?,
    viewModel: AstronomyListViewModel = koinViewModel(),
    onNavigateToDetail: (Astronomy) -> Unit,
    onNavigateToFavorites: () -> Unit,
    onSignOut: () -> Unit,
) {
    val getAstronomyState by viewModel.getAstronomyByDateState.collectAsState()
    val astronomyList by viewModel.astronomyList.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }
    var startDate by remember { mutableStateOf("2025-01-01") }
    var endDate by remember { mutableStateOf("2025-01-10") }

    LaunchedEffect(Unit) {
        if (astronomyList.isEmpty()) {
            viewModel.getAstronomyByDate(startDate, endDate)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "APOD 2025",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = "Selecionar data"
                        )
                    }

                    IconButton(onClick = onSignOut) {
                        Icon(
                            Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Sair"
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Icon(Icons.Default.PhotoLibrary, null)
                    },
                    label = { Text("Galeria") }
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = onNavigateToFavorites,
                    icon = {
                        Icon(Icons.Default.StarBorder, null)
                    },
                    label = { Text("Favoritos") }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (getAstronomyState) {
                is UiState.Loading -> {
                    CircularProgressIndicator()
                }

                is UiState.Success -> {
                    AstronomyListContent(
                        astronomyList = astronomyList,
                        onNavigateToDetail = onNavigateToDetail,
                        onFavorite = { viewModel.toggleFavorite(it) }
                    )
                }

                is UiState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = (getAstronomyState as UiState.Error).message,
                            color = MaterialTheme.colorScheme.error
                        )
                        if (astronomyList.isNotEmpty()) {
                            AstronomyListContent(
                                astronomyList = astronomyList,
                                onNavigateToDetail = onNavigateToDetail,
                                onFavorite = { viewModel.toggleFavorite(it) }
                            )
                        } else {
                            Text(
                                text = (getAstronomyState as UiState.Error).message,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }

                else -> {
                    if (astronomyList.isNotEmpty()) {
                        AstronomyListContent(
                            astronomyList = astronomyList,
                            onNavigateToDetail = onNavigateToDetail,
                            onFavorite = { viewModel.toggleFavorite(it) }
                        )
                    }
                }
            }
        }
    }
}