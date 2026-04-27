package com.example.MoneyManager.Model.All_Withdrawal_data.Language_Color_Lists

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.money.trackpay.R
object GetLanguage_ColorName{
    var arabic = ""
    var english = ""
    var green_theme = ""
    var blue_theme = ""

    @Composable
    fun SetLanguage_ColorName(){
        arabic = stringResource(R.string.arabic)
        english = stringResource(R.string.english)
        green_theme = stringResource(R.string.green_theme)
        blue_theme = stringResource(R.string.blue_theme)
    }
}
val languageList = listOf(
   "العربيه",
  "English",
)

val colorsList = listOf(
    GetLanguage_ColorName.green_theme,
    GetLanguage_ColorName.blue_theme,
)
