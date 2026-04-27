package com.example.xmltools.AppActivitys.flixScreen

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext

enum class WindowType {
    Compact,
    Medium,
    Expanded,
}

enum class OrientationType {
    Portrait,
    Landscape
}

data class DeviceInfo(
    val windowType: WindowType,
    val orientation: OrientationType
)

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun RememberDeviceInfo(): DeviceInfo {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    val windowSizeClass = calculateWindowSizeClass(context as Activity)
    val windowType = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> WindowType.Compact
        WindowWidthSizeClass.Medium -> WindowType.Medium
        WindowWidthSizeClass.Expanded -> WindowType.Expanded
        else -> WindowType.Compact
    }
    val orientation = if (configuration.screenWidthDp > configuration.screenHeightDp) {
        OrientationType.Landscape
    } else {
        OrientationType.Portrait
    }

    return DeviceInfo(windowType, orientation)
}
