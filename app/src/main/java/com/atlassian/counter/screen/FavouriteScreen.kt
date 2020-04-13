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
import com.atlassian.counter.AppState
import com.atlassian.counter.R
import com.atlassian.counter.Screen
import com.atlassian.counter.navigateTo

@Composable
fun FavouriteScreen() {
    Scaffold(
            topAppBar = {
                TopAppBar(
                        title = { Text("Favourite Primes") },
                        navigationIcon = {
                            IconButton(onClick = {
                                navigateTo(Screen.Home)
                            }) {
                                Icon(asset = vectorResource(id = R.drawable.ic_back))
                            }
                        })
            },
            bodyContent = {
                FavouriteScreenBody()
            }
    )
}

@Composable
fun FavouriteScreenBody() {
    VerticalScroller {
        Column {
            AppState.favourites.forEachIndexed { index, favourite ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    ListItem(
                            text = { Text(text = "$favourite")},
                            trailing = {
                                TextButton(onClick = {
                                    AppState.favourites.removeAt(index)
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
    AppState.favourites.addAll(0, listOf(3, 5, 7))
    FavouriteScreen()
}
