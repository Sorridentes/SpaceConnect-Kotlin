package br.com.thefirst.fiap.spaceconnect.navigation

object AppRoutes {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val AUTH = "auth"
    const val HOME = "home"
    const val FAVORITES = "favorites"
    const val DETAIL = "detail/{date}"

    fun astronomyDetail(date: String): String {
        return "detail/$date"
    }
}