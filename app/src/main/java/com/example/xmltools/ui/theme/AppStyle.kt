package com.example.xmltools.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppStyle {
    var textColor : Color = black
    var buttonColor : Color = black
    var textButtonColor : Color = black
    var cardColor : Color = black
    var floatButtonColor : Color = black
    var iconButtonColor : Color = black
    var shadowColor : Color = black
    var listOfBrushColor = listOf(black , black , black)
}
@Composable
 fun Change_Style(){
    val darkTheme = isSystemInDarkTheme()
    if (darkTheme){
        AppStyle.apply {
            textColor = white
            cardColor = gray
            floatButtonColor = sky_blue
            iconButtonColor = black
            buttonColor = sky_blue
            textButtonColor = black
            shadowColor = green
            listOfBrushColor = listOf(blue , black_blue , sky_blue)
        }
    }else{
        AppStyle.apply {
            textColor = black
            floatButtonColor = blue_green
            cardColor = snow
            iconButtonColor = white
            buttonColor = dark_green
            textButtonColor = white
            shadowColor = black_blue
            listOfBrushColor = listOf(dark_green , blue_green , green)
        }
    }
}