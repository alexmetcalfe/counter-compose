package com.atlassian.counter

import com.atlassian.counter.state.Store

sealed class Screen {
    object Home : Screen()
    object Counter: Screen()
    object Favourite: Screen()
}

/**
 * Temporary solution pending navigation support.
 */
fun navigateTo(store: Store<AppState, AppAction>, destination: Screen) {
    store.value.currentScreen = destination
}