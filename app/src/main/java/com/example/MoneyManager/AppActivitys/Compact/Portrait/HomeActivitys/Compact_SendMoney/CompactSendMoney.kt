package com.example.MoneyManager.AppActivitys.Compact.Portrait.HomeActivitys.Compact_SendMoney

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cairo_bold
import cairo_medium
import com.example.MoneyManager.ViewModels.DepositDataViewModel.DepositDataViewModel
import com.example.MoneyManager.ViewModels.Withdrawal_data_VM.WithdrawalDataViewModel
import com.example.MoneyManager.ui.theme.AppStyle
import com.example.MoneyManager.ui.theme.Change_Style
import com.example.MoneyManager.ui.theme.black
import com.example.MoneyManager.ui.theme.blueLight
import com.example.MoneyManager.ui.theme.blue_shadow
import com.example.MoneyManager.ui.theme.darkRed
import com.example.MoneyManager.ui.theme.forestGreen
import com.example.MoneyManager.ui.theme.gray
import com.example.MoneyManager.ui.theme.noColor
import com.example.MoneyManager.ui.theme.snow_shadow
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
        Body(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }

}


@Composable
private fun Body(modifier: Modifier = Modifier) {

    //4 THIS IS ALL WITHDRAWAL DATA :
    val withdrawalDataViewModel: WithdrawalDataViewModel = viewModel()
    val allWithdrawalGetAllData by withdrawalDataViewModel.withdrawalDataFlow.collectAsState()
    val sumOfAllWithdrawalData = allWithdrawalGetAllData.sumOf { it.amount }

    //4 THIS IS ALL DEPOSIT DATA :
    val depositDataViewModel: DepositDataViewModel = viewModel()
    val allDepositData by depositDataViewModel.depositDataFlow.collectAsState()
    val sumOfDepositData = allDepositData.sumOf { it.depositAmount }

    //3 THIS IS THE RESULT OF ( DEPOSIT AND WITHDRAWAL ) :
    val theResult = sumOfDepositData - sumOfAllWithdrawalData

    //` this is the Percentage of the (withdrawal/Depsoit) :
    val percentage = if (sumOfDepositData != 0.0) {
        (sumOfAllWithdrawalData / sumOfDepositData).toFloat()
    } else {
        0f
    }
    //` the animation of the progress :
    val animationOfProgress by animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(
            durationMillis = 8500, easing = FastOutSlowInEasing
        ),
    )

    var phoneNumber by rememberSaveable { mutableStateOf("") }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.Start,

        ) {
        //1  HERE WE SHOW TO USER THE TOTAL MONEY THAT HE HAVE :
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = snow_shadow
                )
                .padding(vertical = 22.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Text(
                    text = stringResource(R.string.total_amount),
                    color = AppStyle.textColor2,
                    fontFamily = cairo_medium
                )
                Text(
                    text = theResult.toString(),
                    color = AppStyle.textColor2,
                    fontFamily = cairo_bold
                )

            }
            CircularProgressIndicator(
                modifier = Modifier.size(62.dp),
                progress = { animationOfProgress },
                color = darkRed,
                trackColor = forestGreen,
                strokeWidth = 6.dp,

                )
        }

        //1 HERE WE GET THE NUMBER THAT HE WILL SEND MONEY TO :
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .border(
                    width = 1.dp,
                    color = snow_shadow
                )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = stringResource(R.string.phone_number),
                    color = AppStyle.textColor2,
                    fontFamily = cairo_medium,
                    fontSize = 12.sp
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = blue_shadow,
                        unfocusedContainerColor = blue_shadow,
                        cursorColor = black,
                    ),
                    textStyle = TextStyle(
                        color = AppStyle.textColor2,
                        fontSize = 16.sp,
                        fontFamily = cairo_medium
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),


                    )

            }
        }

    }


}

@Preview(showBackground = true)
@Composable
fun PreviewThisScreen(modifier: Modifier = Modifier) {

    Change_Style()
    CompactSendMoney(navController = rememberNavController())

}