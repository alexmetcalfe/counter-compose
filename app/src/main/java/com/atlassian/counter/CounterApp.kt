package com.atlassian.counter

import androidx.compose.Composable
import androidx.ui.animation.Crossfade
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.tooling.preview.Preview
import com.atlassian.counter.screen.CounterScreen
import com.atlassian.counter.screen.FavouriteScreen
import com.atlassian.counter.screen.HomeScreen

@Composable
fun CounterApp() {
    MaterialTheme(
            colors = lightThemeColors
    ) {
        AppContent()
    }
}


@Composable
private fun AppContent() {
    Crossfade(AppState.currentScreen) { screen ->
        Surface(color = MaterialTheme.colors.background) {
            when (screen) {
                is Screen.Home -> HomeScreen()
                is Screen.Counter -> CounterScreen()
                is Screen.Favourite -> FavouriteScreen()
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    CounterApp()
}
