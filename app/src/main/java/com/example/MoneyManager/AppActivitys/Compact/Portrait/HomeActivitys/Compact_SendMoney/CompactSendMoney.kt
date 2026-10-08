package com.example.MoneyManager.AppActivitys.Compact.Portrait.HomeActivitys.Compact_SendMoney

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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

import com.example.MoneyManager.Model.All_Withdrawal_data.Withdrawal_Data
import com.example.MoneyManager.ViewModels.DepositDataViewModel.DepositDataViewModel
import com.example.MoneyManager.ViewModels.Withdrawal_data_VM.WithdrawalDataViewModel
import com.example.MoneyManager.ui.theme.AppStyle
import com.example.MoneyManager.ui.theme.Change_Style
import com.example.MoneyManager.ui.theme.black
import com.example.MoneyManager.ui.theme.blue_shadow
import com.example.MoneyManager.ui.theme.darkRed
import com.example.MoneyManager.ui.theme.forestGreen
import com.example.MoneyManager.ui.theme.greenLight
import com.example.MoneyManager.ui.theme.orange
import com.example.MoneyManager.ui.theme.red
import com.example.MoneyManager.ui.theme.sky_blue
import com.example.MoneyManager.ui.theme.snow
import com.example.MoneyManager.ui.theme.snow_shadow
import com.example.MoneyManager.ui.theme.white
import com.money.trackpay.R

@Composable
fun CompactSendMoney(
    modifier: Modifier = Modifier, navController: NavHostController
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding(),

    ) { paddingValues ->
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.add_edit_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,

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

    val context = LocalContext.current

    val the_amount_was_sent_to = stringResource(R.string.the_amount_was_sent_to)
    val send_money = stringResource(R.string.send_money)

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
    var theAmountYouWillSet by rememberSaveable { mutableStateOf("") }
    var THE_LASTVALE_WE_WILL_SEND by rememberSaveable { mutableStateOf("") }
    var makeSureOfUserInfo by rememberSaveable { mutableStateOf(false) }
    var selectedIndex by rememberSaveable { mutableStateOf(0) }
    val encodedHash = Uri.encode("#") //1 this value is to set (#) in Uri so we can send it easy

    val listOfPaymentMethod = listOf<PAYMENTMETHOD>(
        PAYMENTMETHOD(name = "Orange", color = orange, textColor = black),
        PAYMENTMETHOD(name = "Vodafone", color = red, textColor = white),
        PAYMENTMETHOD(name = "We", color = snow, textColor = black),
        PAYMENTMETHOD(name = "Etisalat", color = greenLight, textColor = black),
    )


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
                    width = 1.dp, color = snow_shadow
                )
                .padding(vertical = 2.dp, horizontal = 12.dp),
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
                .padding(horizontal = 0.dp)
                .border(
                    width = 1.dp, color = snow_shadow
                )
        ) {
            //2 HERE WE GET THE PHONE NUMBER :
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 16.dp),
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
                    singleLine = true,
                    onValueChange = { phoneNumber = it },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = blue_shadow,
                        unfocusedContainerColor = blue_shadow,
                        cursorColor = black,
                    ),
                    textStyle = TextStyle(
                        color = AppStyle.textColor2, fontSize = 16.sp, fontFamily = cairo_medium
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                )
            }
            //2 HERE WE GET THE AMOUNT THAT WILL BE SENT :
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = stringResource(R.string.the_amount_you_will_sent),
                    color = AppStyle.textColor2,
                    fontFamily = cairo_medium,
                    fontSize = 12.sp
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = theAmountYouWillSet,
                    singleLine = true,
                    onValueChange = { theAmountYouWillSet = it },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = blue_shadow,
                        unfocusedContainerColor = blue_shadow,
                        cursorColor = black,
                    ),
                    textStyle = TextStyle(
                        color = AppStyle.textColor2, fontSize = 16.sp, fontFamily = cairo_medium
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                )
            }
            //2 HERE USER SLECT PAYMENT METHOD :
            Spacer(modifier = Modifier.height(8.dp))
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(listOfPaymentMethod) { index, data ->
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Button(
                            onClick = { selectedIndex = index + 1 },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = data.color
                            ), shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = data.name, color = data.textColor, fontSize = 12.sp
                            )
                        }

                        Checkbox(
                            checked = selectedIndex == index + 1,
                            onCheckedChange = {
                                if (it) selectedIndex = index + 1
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = sky_blue,
                                checkmarkColor = black,
                                uncheckedColor = red
                            )
                        )

                    }
                }
            }

            //3 HERE WE MAKE USER MAKE SURE OF THE DATA THAT HE ENTERED LIKE PHONE AND MONEY :
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.you_must_make_sure_of_your_entered_info),
                    color = AppStyle.textColor2,
                    fontFamily = cairo_bold,
                    fontSize = 16.sp
                )
                Checkbox(
                    checked = makeSureOfUserInfo,
                    onCheckedChange = { makeSureOfUserInfo = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = sky_blue, checkmarkColor = black, uncheckedColor = red
                    )
                )
            }
            //2 THIS IS SEND BUTTON :
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp),
                    enabled = makeSureOfUserInfo && selectedIndex != 0 && phoneNumber.trim()
                        .isNotEmpty() && theAmountYouWillSet.trim().isNotEmpty(),
                    onClick = {

                        val cleanPhone = phoneNumber.trim()
                        val cleanAmount = theAmountYouWillSet.trim()

                        //1 HERE WE SET THE CODE BEFORE WE SEND IT :
                        val ussdCode = when (selectedIndex) {
                            1 -> "tel:*7115*1*$cleanPhone*$cleanAmount$encodedHash" //4 Orange
                            2 -> "tel:*9*7*$cleanPhone*$cleanAmount$encodedHash"    //4 Vodafone
                            3 -> "tel:*990*$cleanPhone*$cleanAmount$encodedHash"    //4 WE
                            4 -> "tel:*777*1*$cleanPhone*$cleanAmount$encodedHash"  //4 Etisalat
                            else -> {
                                return@Button
                            }
                        }

                        if (ussdCode.isNotEmpty()) {
                            try {
                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse(ussdCode)
                                }
                                context.startActivity(intent)

                                withdrawalDataViewModel.addNewData(
                                    context = context,
                                    locateDataBase = Withdrawal_Data().apply {

                                        amount = theAmountYouWillSet.toDouble()
                                        type = phoneNumber
                                        items = "$the_amount_was_sent_to $phoneNumber"
                                    },
                                ) {

                                }

                            } catch (e: Exception) {

                            }
                        }

                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppStyle.buttonColor,
                    )

                ) {
                    Text(
                        text = stringResource(R.string.send_money),
                        fontFamily = cairo_medium,
                        fontSize = 14.sp,
                        color = AppStyle.textColor2
                    )
                }
            }
        }
    }
}


data class PAYMENTMETHOD(
    val name: String,
    val color: Color,
    val textColor: Color,

    )


@Preview(showBackground = true)
@Composable
fun PreviewThisScreen(modifier: Modifier = Modifier) {

    Change_Style()
    CompactSendMoney(navController = rememberNavController())

}

