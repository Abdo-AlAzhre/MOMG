package com.example.MoneyManager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppStyle {
    var textColor: Color = black
    var textColor2: Color = black
    var textColor3: Color = black

    var textColor4: Color = black
    var iconColor: Color = black
    var outLinText: Color = black

    var outLinBackground: Color = black
    var cardColor: Color = black
    var alertDialogColor: Color = black

    var buttonColor: Color = black
    var buttonWithTextColor: Color = black
    var textButtonColor: Color = black

    var floatButtonColor: Color = black
    var iconButtonColor: Color = black
    var shadowColor: Color = black

    var listOfBrushColor = listOf(black, black, black)
    var ModalNavigationDrawerColor = listOf(light_sky_blue, light_blue, soft_blue)
    var homeBackGround = listOf(soft_blue, light_blue, light_sky_blue)

    var addBackGround = listOf(soft_blue, light_blue, light_sky_blue)
    var moreINFBackGround = listOf(soft_blue, light_blue, light_sky_blue)
    var monthBackGround = listOf(lightSkyBlue, softBlue, blueExtraLight)

    var toDoBackground = listOf(lightSkyBlue, softBlue, blueExtraLight)
    var cardOfDrawerSheet = black
    var topBarBackground = black_blue

    var dialogColor: Color = black
    var bridgeColor = listOf(bloodRed, darkRed, brightRed, lightRoseRed, softRed, softRed)

}

@Composable
fun Change_Style(darkTheme: Boolean = isSystemInDarkTheme()) {

    if (darkTheme) {
        AppStyle.apply {
            textColor = white
            textColor2 = black
            textColor3 = black

            textColor4 = white
            iconColor = black_blue
            outLinText = black

            cardColor = snow_shadow
            alertDialogColor = gray
            floatButtonColor = soft_blue

            iconButtonColor = black
            buttonColor = blueLight
            buttonWithTextColor = snow

            textButtonColor = black
            shadowColor = green
            outLinBackground = snow_shadow

            listOfBrushColor = listOf(blue, black_blue, sky_blue)
            ModalNavigationDrawerColor = listOf(blueExtraLight, blueExtraLight, softBlue)
            homeBackGround = listOf(black_blue, black_blue, black)

            addBackGround = listOf(black, black_blue, black_blue)
            moreINFBackGround = listOf(navyBlueBlack, black, black)
            monthBackGround = listOf(midnightBlue, midnightBlue, black, black)

            toDoBackground = listOf(navyDarker, almostBlackBlue, almostBlackBlue)
            cardOfDrawerSheet = light_blue
            topBarBackground = black_blue

            dialogColor = alertDialogColor
        }
    } else {
        AppStyle.apply {
            textColor = white
            textColor2 = black
            textColor3 = white

            textColor4 = black
            outLinText = black
            iconColor = black

            cardColor = snow_shadow
            alertDialogColor = snow
            floatButtonColor = lightSkyBlue

            iconButtonColor = black
            buttonColor = blueExtraLight
            buttonWithTextColor = snow

            textButtonColor = black
            shadowColor = black_blue
            outLinBackground = snow_shadow

            listOfBrushColor = listOf(blue, blue, sky_blue)
            ModalNavigationDrawerColor = listOf(blueExtraLight, blueExtraLight, softBlue)
            homeBackGround = listOf(blueExtraLight, blueExtraLight, softBlue)

            addBackGround = listOf(white, blueLight, softBlue, softBlue, blueExtraLight)
            monthBackGround = listOf(lightSkyBlue, softBlue, blueExtraLight, blueExtraLight)
            toDoBackground = listOf(greenExtraLight, blueExtraLight, blueLight, softBlue, softBlue)

            cardOfDrawerSheet = blueLight
            topBarBackground = blueExtraLight
            dialogColor = alertDialogColor

        }
    }
}


