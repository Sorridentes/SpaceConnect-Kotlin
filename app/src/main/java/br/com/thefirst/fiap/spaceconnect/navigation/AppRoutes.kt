package br.com.thefirst.fiap.spaceconnect.navigation

object AppRoutes {
    const val AUTH = "auth"
    const val HOME = "home"

    const val DETAIL = "detail/{date}"

    fun astronomyDetail(date: String): String {
        return "detail/$date"
    }
}