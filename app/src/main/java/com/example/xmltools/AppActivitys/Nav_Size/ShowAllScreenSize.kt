package com.example.xmltools.AppActivity

import androidx.compose.runtime.Composable
import com.example.xmltools.AppActivity.Compact.Portrait.Compact_Nav
import com.example.xmltools.AppActivity.flixScreen.OrientationType
import com.example.xmltools.AppActivity.flixScreen.RememberDeviceInfo
import com.example.xmltools.AppActivity.flixScreen.WindowType

@Composable
fun ShowScreens() {
    val deviceInfo = RememberDeviceInfo()
    when (deviceInfo.windowType) {
        WindowType.Compact -> {
            if (deviceInfo.orientation == OrientationType.Portrait) {
                Compact_Nav()
            } else {
                Compact_Nav()
            }
        }

        WindowType.Medium -> {
            Compact_Nav()
        }

        WindowType.Expanded -> {
            Compact_Nav()
        }
    }
}