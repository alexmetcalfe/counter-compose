package com.atlassian.counter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import com.atlassian.counter.state.Store

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TODO retain store
            CounterApp(store = Store(initialValue = AppState(), reducer = ::appReducer))
        }
    }
}

