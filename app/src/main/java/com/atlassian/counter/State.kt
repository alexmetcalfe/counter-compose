package com.atlassian.counter

import androidx.compose.Model

@Model
object AppState {
    var currentScreen: Screen = Screen.Home
    var counter: Int = 0
    var favourites: MutableList<Int> = mutableListOf()
}
