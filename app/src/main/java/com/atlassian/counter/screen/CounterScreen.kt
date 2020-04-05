package com.atlassian.counter.screen

import android.icu.text.MessageFormat
import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.LayoutAlign
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.LayoutSize
import androidx.ui.layout.Row
import androidx.ui.material.AlertDialog
import androidx.ui.material.Scaffold
import androidx.ui.material.TextButton
import androidx.ui.material.TopAppBar
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.atlassian.counter.AppState
import com.atlassian.counter.AppState.counter
import com.atlassian.counter.R
import com.atlassian.counter.Screen
import com.atlassian.counter.VectorImageButton
import com.atlassian.counter.navigateTo
import com.atlassian.wolfram.data.network.nthPrime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.sqrt

@Composable
fun CounterScreen() {
    Column {
        Scaffold(
                topAppBar = {
                    TopAppBar(
                            title = { Text("Counter") },
                            navigationIcon = {
                                VectorImageButton(R.drawable.ic_back) {
                                    navigateTo(Screen.Home)
                                }
                            })
                },
                bodyContent = {
                    CounterScreenBody()
                }
        )
    }
}

@Composable
private fun CounterScreenBody() {
    var showIsPrimeDialog by state { false }
    var alertNthPrime by state<Int?> { null }
    var nthPrimeButtonEnabled by state { true }
    Column(modifier = LayoutSize.Fill + LayoutAlign.Center) {
        Row {
            TextButton(onClick = { counter-- }) {
                Text(text = "-")
            }

            Text(text = "$counter", modifier = LayoutPadding(8.dp))

            TextButton(onClick = { counter++ }) {
                Text(text = "+")
            }
        }
        Row {
            TextButton(onClick = {
                showIsPrimeDialog = true
            }) {
                Text(text = "Is this prime?")
            }
        }
        Row {
            TextButton(
                    enabled = nthPrimeButtonEnabled,
                    onClick = {
                        val counterVal = counter
                        nthPrimeButtonEnabled = false
                        CoroutineScope(Dispatchers.Main).launch {
                            val result =
                                    withContext(Dispatchers.IO) {
                                        nthPrime(counterVal)
                                    }

                            alertNthPrime = result
                            nthPrimeButtonEnabled = true
                        }
                    }) {
                Text(text = "What is the ${ordinal(counter)} prime?")
            }
        }

        if (showIsPrimeDialog) {
            AlertDialog(onCloseRequest = {
                showIsPrimeDialog = false
            }, text = {}) {
                Column(modifier = LayoutPadding(16.dp)) {
                    if (isPrime(counter)) {
                        Text("$counter is prime :-)")
                    } else {
                        Text("$counter is not prime :(")
                    }
                }
                Column {
                    if (AppState.favourites.contains(counter)) {
                        TextButton(onClick = {
                            AppState.favourites.remove(counter)
                            showIsPrimeDialog = false
                        }, modifier = LayoutPadding(8.dp)) {
                            Text(text = "Remove from favourite primes")
                        }
                    } else {
                        TextButton(onClick = {
                            AppState.favourites.add(counter)
                            showIsPrimeDialog = false
                        }, modifier = LayoutPadding(8.dp)) {
                            Text(text = "Save to favourite primes")
                        }
                    }
                }
                Column {
                    TextButton(onClick = {
                        showIsPrimeDialog = false
                    }, modifier = LayoutPadding(8.dp)) {
                        Text(text = "Ok")
                    }
                }
            }
        }

        if (alertNthPrime != null) {
            AlertDialog(
                    onCloseRequest = { alertNthPrime = null },
                    title = {
                        Text(text = "The ${ordinal(counter)} prime is $alertNthPrime")
                    },
                    text = {},
                    confirmButton = {
                        TextButton(onClick = {
                            alertNthPrime = null
                        }) {
                            Text("Ok")
                        }
                    },
                    dismissButton = null
            )
        }

    }
}

private fun isPrime(number: Int): Boolean {
    if (number <= 1) return false
    if (number <= 3) return true
    for (i in 2..sqrt(number.toDouble()).toInt()) {
        if (number % i == 0) return false
    }
    return true
}

private fun ordinal(number: Int): String {
    return MessageFormat.format("{0,ordinal}", number)
}

@Preview("Counter screen")
@Composable
fun PreviewCounter() {
    CounterScreen()
}

