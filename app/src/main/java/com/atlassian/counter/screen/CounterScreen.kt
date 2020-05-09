package com.atlassian.counter.screen

import android.icu.text.MessageFormat
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.padding
import androidx.ui.layout.wrapContentSize
import androidx.ui.material.AlertDialog
import androidx.ui.material.IconButton
import androidx.ui.material.OutlinedButton
import androidx.ui.material.Scaffold
import androidx.ui.material.TextButton
import androidx.ui.material.TopAppBar
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.atlassian.counter.AppAction
import com.atlassian.counter.AppAction.CounterAction
import com.atlassian.counter.AppState
import com.atlassian.counter.R
import com.atlassian.counter.Screen
import com.atlassian.counter.appReducer
import com.atlassian.counter.navigateTo
import com.atlassian.counter.state.Store
import com.atlassian.wolfram.data.network.nthPrime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.sqrt

@Composable
fun CounterScreen(store: Store<AppState, AppAction>) {
    Column {
        Scaffold(
                topAppBar = {
                    TopAppBar(
                            title = { Text("Counter Demo") },
                            navigationIcon = {
                                IconButton(onClick = {
                                    navigateTo(store, Screen.Home)
                                }) {
                                    Icon(asset = vectorResource(id = R.drawable.ic_back))
                                }
                            })
                },
                bodyContent = {
                    CounterScreenBody(store)
                }
        )
    }
}

@Composable
private fun CounterScreenBody(store: Store<AppState, AppAction>) {
    var showIsPrimeDialog by state { false }
    var alertNthPrime by state<Int?> { null }
    var nthPrimeButtonEnabled by state { true }

    Column(modifier = Modifier.fillMaxSize() + Modifier.wrapContentSize(Alignment.Center)) {
        Row {
            OutlinedButton(onClick = { store.send(CounterAction.DecrTapped) }) {
                Text(text = "-")
            }

            Text(text = "${store.value.count}", modifier = Modifier.padding(8.dp))

            OutlinedButton(onClick = { store.send(CounterAction.IncrTapped) }) {
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
                        val counterVal = store.value.count
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
                Text(text = "What is the ${ordinal(store.value.count)} prime?")
            }
        }

        if (showIsPrimeDialog) {
            AlertDialog(
                    onCloseRequest = {
                        showIsPrimeDialog = false
                    },
                    text = {
                        Column(modifier = Modifier.padding(16.dp)) {
                            if (isPrime(store.value.count)) {
                                Text("${store.value.count} is prime :-)")
                            } else {
                                Text("${store.value.count} is not prime :(")
                            }
                        }
                    },
                    buttons = {
                        if (isPrime(store.value.count)) {
                            Column {
                                if (store.value.favouritePrimes.contains(store.value.count)) {
                                    TextButton(onClick = {
                                        store.send(AppAction.PrimeDialogAction.RemoveFavouritePrimeTapped)
                                        showIsPrimeDialog = false
                                    }, modifier = Modifier.padding(8.dp)) {
                                        Text(text = "Remove from favourite primes")
                                    }
                                } else {
                                    TextButton(onClick = {
                                        store.send(AppAction.PrimeDialogAction.SaveFavouritePrimeTapped)
                                        showIsPrimeDialog = false
                                    }, modifier = Modifier.padding(8.dp)) {
                                        Text(text = "Save to favourite primes")
                                    }
                                }
                            }
                        }
                        Column {
                            TextButton(onClick = {
                                showIsPrimeDialog = false
                            }, modifier = Modifier.padding(8.dp)) {
                                Text(text = "Ok")
                            }
                        }
                    }
            )
        }

        if (alertNthPrime != null) {
            AlertDialog(
                    onCloseRequest = { alertNthPrime = null },
                    title = {
                        Text(text = "The ${ordinal(store.value.count)} prime is $alertNthPrime")
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
    CounterScreen(Store(initialValue = AppState(), reducer = ::appReducer))
}

