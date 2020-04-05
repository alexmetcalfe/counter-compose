package com.atlassian.counter.screen

import androidx.compose.Composable
import androidx.compose.remember
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.material.Scaffold
import androidx.ui.material.ScaffoldState
import androidx.ui.material.TextButton
import androidx.ui.material.TopAppBar
import androidx.ui.tooling.preview.Preview
import com.atlassian.counter.Screen
import com.atlassian.counter.navigateTo

@Composable
fun HomeScreen(
        scaffoldState: ScaffoldState = remember { ScaffoldState() }
) {
    Column {
        Scaffold(
                scaffoldState = scaffoldState,
                topAppBar = {
                    TopAppBar(title = { Text("State management") })
                },
                bodyContent = {
                    HomeScreenBody()
                }
        )
    }
}

@Composable
private fun HomeScreenBody() {
    Column {
        TextButton(onClick = {
            navigateTo(Screen.Counter)
        }) {
            Text(text = "Counter demo")
        }
        TextButton(onClick = {
            navigateTo(Screen.Favourite)
        }) {
            Text(text = "Favourite primes")
        }
    }
}

@Preview("Home Screen")
@Composable
fun PreviewHome() {
    HomeScreen()
}
