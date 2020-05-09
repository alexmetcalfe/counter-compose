package com.atlassian.counter

import androidx.compose.Model
import androidx.compose.frames.ModelList
import androidx.compose.frames.modelListOf
import com.atlassian.counter.AppAction.CounterAction
import com.atlassian.counter.AppAction.FavouritePrimesAction
import com.atlassian.counter.AppAction.PrimeDialogAction
import java.util.Date

// TODO can we have pojos without model?
@Model
data class AppState(
        var currentScreen: Screen = Screen.Home,
        var count: Int = 0,
        var favouritePrimes: ModelList<Int> = modelListOf(),
        var activityFeed: ModelList<Activity> = modelListOf(),
        var loggedInUser: User? = null
)

data class User(
        val id: Int,
        val name: String,
        val bio: String
)

data class Activity(
        val timestamp: Date = Date(),
        val type: ActivityType
) {
    sealed class ActivityType {
        data class AddedFavouritePrime(val value: Int) : ActivityType()
        data class RemovedFavouritePrime(val value: Int) : ActivityType()
    }
}

sealed class AppAction {

    sealed class CounterAction : AppAction() {
        object DecrTapped : CounterAction()
        object IncrTapped : CounterAction()
    }

    sealed class PrimeDialogAction : AppAction() {
        object SaveFavouritePrimeTapped : PrimeDialogAction()
        object RemoveFavouritePrimeTapped : PrimeDialogAction()
    }

    sealed class FavouritePrimesAction : AppAction() {
        data class DeleteFavouritePrime(val index: Int) : FavouritePrimesAction()
    }
}

// TODO equivalent to inout in swift in maybe Arrow??
fun appReducer(state: AppState, action: AppAction): AppState {
    return when (action) {
        is CounterAction.DecrTapped -> {
            state.copy(
                    count = state.count - 1
            )
        }
        is CounterAction.IncrTapped -> {
            state.copy(
                    count = state.count + 1
            )
        }
        is PrimeDialogAction.SaveFavouritePrimeTapped -> {
            state.apply {
                activityFeed.add(
                        Activity(
                                type = Activity.ActivityType.AddedFavouritePrime(state.count)
                        )
                )
                favouritePrimes.add(state.count)
            }
        }
        is PrimeDialogAction.RemoveFavouritePrimeTapped -> {
            state.apply {
                activityFeed.add(
                        Activity(
                                type = Activity.ActivityType.RemovedFavouritePrime(state.count)
                        )
                )
                favouritePrimes.remove(state.count)
            }
        }
        is FavouritePrimesAction.DeleteFavouritePrime -> {
            state.apply {
                activityFeed.add(
                        Activity(
                                type = Activity.ActivityType.RemovedFavouritePrime(state.count)
                        )
                )
                favouritePrimes.removeAt(action.index)
            }
        }
    }
}