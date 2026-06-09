package br.com.thefirst.fiap.spaceconnect.features.nasa.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.repository.onboardingPages
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AstronomyOnboardingScreen(
    onNavigateToAuth: () -> Unit,
    viewModel: AstronomyOnboardingViewModel = koinViewModel()
) {
    val isCompleted by viewModel.isOnboardingCompleted.collectAsState()

    if (isCompleted) {
        onNavigateToAuth()
        return
    }

    val pagerState = rememberPagerState(
        pageCount = { onboardingPages.size }
    )

    val scope = rememberCoroutineScope()

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            HorizontalPager(
                state = pagerState
            ) { page ->
                AstronomyOnboardingContent(
                    page = onboardingPages[page]
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                PagerIndicator(
                    currentPage = pagerState.currentPage,
                    totalPages = onboardingPages.size
                )

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (pagerState.currentPage > 0) {
                        TextButton(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        pagerState.currentPage - 1
                                    )
                                }
                            }
                        ) {
                            Text("Voltar")
                        }
                    }

                    Spacer(Modifier.weight(1f))

                    Button(
                        shape = RoundedCornerShape(50),
                        onClick = {
                            scope.launch {
                                if (pagerState.currentPage == onboardingPages.lastIndex) {
                                    viewModel.completeOnboarding()
                                    onNavigateToAuth()
                                } else {
                                    pagerState.animateScrollToPage(
                                        pagerState.currentPage + 1
                                    )
                                }
                            }
                        }
                    ) {
                        Text(
                            if (pagerState.currentPage == onboardingPages.lastIndex)
                                "Começar"
                            else
                                "Próximo"
                        )
                    }
                }
            }
        }
    }
}