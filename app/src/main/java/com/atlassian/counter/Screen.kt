package com.atlassian.counter

sealed class Screen {
    object Home : Screen()
    object Counter: Screen()
    object Favourite: Screen()
}

/**
 * Temporary solution pending navigation support.
 */
fun navigateTo(destination: Screen) {
    AppState.currentScreen = destination
}