package com.example.MoneyManager.AppActivitys.Compact.Portrait.HomeActivitys.Compact_SendMoney

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.money.trackpay.R

@Composable
fun CompactSendMoney(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding(),
        ) { paddingValues ->
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.back_ground1),
            contentDescription = null,
            contentScale = ContentScale.Crop

        )
        Body(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues))
    }

}


@Composable
fun TobBar(modifier: Modifier = Modifier) {

}

@Composable
private fun Body(modifier: Modifier = Modifier) {





}