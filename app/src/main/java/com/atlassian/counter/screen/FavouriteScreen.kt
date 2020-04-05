package com.atlassian.counter.screen

import androidx.compose.Composable
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.Row
import androidx.ui.material.Scaffold
import androidx.ui.material.TopAppBar
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.atlassian.counter.AppState
import com.atlassian.counter.R
import com.atlassian.counter.Screen
import com.atlassian.counter.VectorImageButton
import com.atlassian.counter.navigateTo

@Composable
fun FavouriteScreen() {
    Scaffold(
            topAppBar = {
                TopAppBar(
                        title = { Text("Favourites") },
                        navigationIcon = {
                            VectorImageButton(R.drawable.ic_back) {
                                navigateTo(Screen.Home)
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
        Column(modifier = LayoutPadding(top = 16.dp)) {
            AppState.favourites.forEach { favourite ->
                Row {
                    Text(text = "$favourite")
                }
            }
        }
    }
}

@Preview
@Composable
fun FavouriteScreenPreview() {
    FavouriteScreen()
}
