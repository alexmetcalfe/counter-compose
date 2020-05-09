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
import com.atlassian.counter.AppAction
import com.atlassian.counter.AppState
import com.atlassian.counter.Screen
import com.atlassian.counter.appReducer
import com.atlassian.counter.navigateTo
import com.atlassian.counter.state.Store

@Composable
fun HomeScreen(
        store: Store<AppState, AppAction>,
        scaffoldState: ScaffoldState = remember { ScaffoldState() }
) {
    Column {
        Scaffold(
                scaffoldState = scaffoldState,
                topAppBar = {
                    TopAppBar(title = { Text("State management") })
                },
                bodyContent = {
                    HomeScreenBody(store)
                }
        )
    }
}

@Composable
private fun HomeScreenBody(store: Store<AppState, AppAction>) {
    Column {
        TextButton(onClick = {
            navigateTo(store, Screen.Counter)
        }) {
            Text(text = "Counter demo")
        }
        TextButton(onClick = {
            navigateTo(store, Screen.Favourite)
        }) {
            Text(text = "Favourite primes")
        }
    }
}

@Preview("Home Screen")
@Composable
fun PreviewHome() {
    HomeScreen(Store(initialValue = AppState(), reducer = ::appReducer))
}
