// MainActivity.kt
package com.mega.appcheckinout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mega.appcheckinout.navigation.CheckInOutApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheckInOutApp()
        }
    }
}