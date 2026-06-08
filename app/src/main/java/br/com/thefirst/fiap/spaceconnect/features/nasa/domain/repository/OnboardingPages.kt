package br.com.thefirst.fiap.spaceconnect.features.nasa.domain.repository

import br.com.thefirst.fiap.spaceconnect.R
import br.com.thefirst.fiap.spaceconnect.features.nasa.domain.model.OnboardingPage

val onboardingPages = listOf(
    OnboardingPage(
        image = R.drawable.onboarding_1,
        title = "Explore the Universe",
        description = "Embark on a daily journey through the cosmos with the most stunning images captured by NASA."
    ),
    OnboardingPage(
        image = R.drawable.onboarding_2,
        title = "Daily Astronomy",
        description = "See the astronomical photo of the day and delve into the mysteries of the universe with expert curation and high-resolution images."
    ),
    OnboardingPage(
        image = R.drawable.onboarding_3,
        title = "Space Gallery",
        description = "Browse through days of NASA images and discover the secrets hidden in each astronomical record."
    ),
    OnboardingPage(
        image = R.drawable.onboarding_4,
        title = "Favorites",
        description = "Save the images you love most."
    )
)