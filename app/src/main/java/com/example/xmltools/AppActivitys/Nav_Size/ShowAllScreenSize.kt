package com.example.xmltools.AppActivitys.Nav_Size

import androidx.compose.runtime.Composable
import com.example.xmltools.AppActivitys.flixScreen.OrientationType
import com.example.xmltools.AppActivitys.flixScreen.RememberDeviceInfo
import com.example.xmltools.AppActivitys.flixScreen.WindowType

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