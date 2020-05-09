package com.atlassian.counter.screen

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxWidth
import androidx.ui.material.IconButton
import androidx.ui.material.ListItem
import androidx.ui.material.Scaffold
import androidx.ui.material.TextButton
import androidx.ui.material.TopAppBar
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import com.atlassian.counter.AppAction
import com.atlassian.counter.AppState
import com.atlassian.counter.R
import com.atlassian.counter.Screen
import com.atlassian.counter.appReducer
import com.atlassian.counter.navigateTo
import com.atlassian.counter.state.Store

@Composable
fun FavouriteScreen(store: Store<AppState, AppAction>) {
    Scaffold(
            topAppBar = {
                TopAppBar(
                        title = { Text("Favourite Primes") },
                        navigationIcon = {
                            IconButton(onClick = {
                                navigateTo(store, Screen.Home)
                            }) {
                                Icon(asset = vectorResource(id = R.drawable.ic_back))
                            }
                        })
            },
            bodyContent = {
                FavouriteScreenBody(store)
            }
    )
}

@Composable
fun FavouriteScreenBody(store: Store<AppState, AppAction>) {
    VerticalScroller {
        Column {
            store.value.favouritePrimes.forEachIndexed { index, favourite ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    ListItem(
                            text = { Text(text = "$favourite") },
                            trailing = {
                                TextButton(onClick = {
                                    store.send(
                                            AppAction.FavouritePrimesAction.DeleteFavouritePrime(
                                                    index = index
                                            )
                                    )
                                }) {
                                    Text(text = "Delete")
                                }
                            }

                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FavouriteScreenPreview() {
    val appState = AppState()
    appState.favouritePrimes.addAll(0, listOf(3, 5, 7))
    FavouriteScreen(Store(initialValue = appState, reducer = ::appReducer))
}
