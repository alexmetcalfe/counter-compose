package com.atlassian.counter

import androidx.compose.Composable
import androidx.ui.animation.Crossfade
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.tooling.preview.Preview
import com.atlassian.counter.screen.CounterScreen
import com.atlassian.counter.screen.FavouriteScreen
import com.atlassian.counter.screen.HomeScreen
import com.atlassian.counter.state.Store

@Composable
fun CounterApp(store: Store<AppState, AppAction>) {
    MaterialTheme(
            colors = lightThemeColors
    ) {
        AppContent(store = store)
    }
}


@Composable
private fun AppContent(store: Store<AppState, AppAction>) {
    Crossfade(store.value.currentScreen) { screen ->
        Surface(color = MaterialTheme.colors.background) {
            when (screen) {
                is Screen.Home -> HomeScreen(store)
                is Screen.Counter -> CounterScreen(store)
                is Screen.Favourite -> FavouriteScreen(store)
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    CounterApp(Store(initialValue = AppState(), reducer = ::appReducer))
}
