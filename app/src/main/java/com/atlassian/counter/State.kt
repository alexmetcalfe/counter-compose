package com.atlassian.counter

import androidx.compose.Model
import androidx.compose.frames.modelListOf

@Model
object AppState {
    var currentScreen: Screen = Screen.Home
    var counter: Int = 0
    var favourites = modelListOf<Int>()
}
