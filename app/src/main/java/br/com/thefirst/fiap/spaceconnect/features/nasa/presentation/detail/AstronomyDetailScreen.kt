package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.thefirst.fiap.spaceconnect.common.UiState
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstronomyDetailScreen(
    date: String,
    onBack: () -> Unit,
    viewModel: AstronomyDetailViewModel = koinViewModel()
) {
    val astronomyState by viewModel.astronomyState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadAstronomy(date)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Detalhamento Astronomico",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF0B3D91),
                    navigationIconContentColor = Color(0xFF0B3D91)
                )
            )
        }
    ) { innerPadding ->
        when (astronomyState) {
            is UiState.Loading ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            is UiState.Success -> {
                val astronomy = (astronomyState as UiState.Success).data

                val dateFormatted = try {
                    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
                    LocalDate.parse(astronomy.date, inputFormatter)
                        .format(outputFormatter).uppercase()
                } catch (e: Exception) {
                    astronomy.date
                }

                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                    ) {
                        AsyncImage(
                            model = astronomy.image,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        IconButton(
                            onClick = { viewModel.toggleFavorite(astronomy) },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp)
                                .background(
                                    color = Color.Black.copy(alpha = 0.4f),
                                    shape = RoundedCornerShape(50)
                                )
                        ) {
                            Icon(
                                imageVector = if (astronomy.favorite) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = "Favorito",
                                tint = if (astronomy.favorite) Color(0xFF631FD2) else Color.White
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFF2F2F2),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.DateRange,
                                contentDescription = "Data",
                                tint = Color(0xFF5A5A5A),
                                modifier = Modifier.size(16.dp)
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = dateFormatted,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF5A5A5A)
                            )
                        }
                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = astronomy.title,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFF0B3D91),
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = astronomy.description,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (astronomyState as UiState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            else -> {}
        }
    }
}