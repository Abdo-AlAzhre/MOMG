package com.example.MoneyManager.AppActivitys.Compact.Portrait.HomeActivitys.Monthly_Setting

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import cairo_bold
import cairo_medium
import com.example.MoneyManager.AppADS.BannerADS
import com.example.MoneyManager.Model.All_Withdrawal_data.listOfMonths
import com.example.MoneyManager.Model.All_Withdrawal_data.listOfYears
import com.example.MoneyManager.ViewModels.Annual_Monthly_VM.MonthlyViewModel
import com.example.MoneyManager.ui.theme.AppStyle
import com.example.MoneyManager.ui.theme.black
import com.example.MoneyManager.ui.theme.blue_green
import com.example.MoneyManager.ui.theme.blue_shadow
import com.example.MoneyManager.ui.theme.darkRed
import com.example.MoneyManager.ui.theme.dark_blue_shadow
import com.example.MoneyManager.ui.theme.forestGreen
import com.example.MoneyManager.ui.theme.red
import com.money.trackpay.R
import readexpro_bold
import readexpro_medium
import java.util.Calendar

@Composable
fun Compact_Monthly(navController: NavHostController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding(),
        bottomBar = {
            BannerADS(modifier = Modifier)
        }
    ) { innerPadding ->
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.munthly_background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Compact_MonthlyBody(modifier = Modifier.padding(innerPadding))
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun Compact_MonthlyBody(
    modifier: Modifier = Modifier,
) {

    val calendar = Calendar.getInstance()
    var selectedMonth by remember { mutableIntStateOf(calendar.get(Calendar.MONTH) + 1) }
    var selectedYear by remember { mutableIntStateOf(calendar.get(Calendar.YEAR)) }

    var expandedMonth by remember { mutableStateOf(false) }
    var expandedYear by remember { mutableStateOf(false) }

    //1 this vareable to use monthly ViewModel :
    val viewModelMonthly: MonthlyViewModel = viewModel()
    //1 here we get withdrawal amount monthly :
    val withdrawalMonthsData by viewModelMonthly.getWithdrawalMonthData(selectedMonth, selectedYear)
        .collectAsState(initial = emptyList())
    //1 here we get deposit amount monthly :
    val depositMonthsData by viewModelMonthly.getDepositMonthData(selectedMonth, selectedYear)
        .collectAsState(initial = emptyList())
    /*` here we get the total of all amounts */
    //1 here we get sum of withdrawal amount in this munth :
    val totalOfWithdrawalAmount =
        viewModelMonthly.getTotalWithdrawalMonthAmount(withdrawalMonthsData)
    //1 here we get sum of deposit amount in this munth :
    val totalOfDepositAmount =
        viewModelMonthly.getTotalDepositMonthAmount(depositMonthsData)
    //1 here we divide withdrawal into deposit :
    val totalOfAllAmount = if (totalOfDepositAmount != 0.0) {
        ((totalOfWithdrawalAmount / totalOfDepositAmount)).toFloat()
    } else {
        0f
    }

    val animatedProgress by animateFloatAsState(
        targetValue = totalOfAllAmount,
        animationSpec = tween(
            durationMillis = 8000,
            easing = FastOutSlowInEasing
        ),
        label = "animation"
    )

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //` here we show circularProgress to show the total Deposit and total withdrawal
        Spacer(Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,

        )
        {

            CircularProgressIndicator(
                modifier = Modifier.size(80.dp),
                color = darkRed,
                trackColor = forestGreen,
                progress = {
                    animatedProgress
                },
                strokeWidth = 8.dp,
                gapSize = (-10).dp,

                )
            Spacer(Modifier.width(12.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "${stringResource(R.string.deposit)} : $totalOfDepositAmount",
                    color = black,
                    fontSize = 16.sp,
                    fontFamily = cairo_bold,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "${stringResource(R.string.withdrawal)} : $totalOfWithdrawalAmount",
                    color = black,
                    fontSize = 16.sp,
                    fontFamily = cairo_bold,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Spacer(Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            //` here we get month from user :
            Box(modifier = Modifier.weight(1f)) {
                TextField(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            brush = Brush.sweepGradient(
                                colors = AppStyle.listOfBrushColor
                            ),
                            shape = RoundedCornerShape(1.dp)
                        )
                        .clickable {
                            expandedMonth = true
                        },
                    enabled = false,
                    value = selectedMonth.toString(),
                    onValueChange = {},
                    label = {
                        Text(
                            text = stringResource(R.string.month),
                            color = AppStyle.textColor2
                        )
                    },
                    textStyle = TextStyle(color = AppStyle.textColor2),
                    trailingIcon = {
                        IconButton(
                            onClick = { expandedMonth = true }) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_date_range_24),
                                contentDescription = "",
                                tint = black
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = blue_shadow,
                        unfocusedContainerColor = blue_shadow,
                        cursorColor = blue_green,
                        disabledContainerColor = blue_shadow
                    )
                )
                //` this is list of month numbers :
                DropdownMenu(
                    modifier = Modifier.height(350.dp),
                    expanded = expandedMonth,
                    onDismissRequest = { expandedMonth = false },
                    containerColor = dark_blue_shadow,
                ) {
                    listOfMonths.forEach { theMonth ->
                        DropdownMenuItem(text = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = theMonth,
                                    fontSize = 16.sp,
                                    fontFamily = cairo_medium,
                                    color = AppStyle.textColor
                                )
                            }
                        }, onClick = {
                            selectedMonth = theMonth.toInt()
                            expandedMonth = false
                        })
                    }
                }
            }

            Spacer(Modifier.width(10.dp))
            //` here we get year from user :
            Box(modifier = Modifier.weight(1f)) {
                TextField(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            brush = Brush.sweepGradient(
                                colors = AppStyle.listOfBrushColor
                            ),
                            shape = RoundedCornerShape(1.dp)
                        )
                        .clickable {
                            expandedYear = true
                        },
                    enabled = false,
                    value = selectedYear.toString(),
                    onValueChange = {},
                    label = {
                        Text(
                            text = stringResource(R.string.year),
                            color = AppStyle.textColor2
                        )
                    },
                    textStyle = TextStyle(color = AppStyle.textColor2),
                    trailingIcon = {
                        IconButton(
                            onClick = { expandedYear = true }) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_date_range_24),
                                contentDescription = "",
                                tint = black
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = blue_shadow,
                        unfocusedContainerColor = blue_shadow,
                        cursorColor = blue_green,
                        disabledContainerColor = blue_shadow
                    )
                )
                //` this list of years :
                DropdownMenu(
                    modifier = Modifier.height(350.dp),
                    expanded = expandedYear,
                    onDismissRequest = { expandedYear = false },
                    containerColor = dark_blue_shadow,
                ) {
                    listOfYears.forEach { theYear ->
                        DropdownMenuItem(text = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = theYear.toString(),
                                    fontSize = 16.sp,
                                    fontFamily = cairo_medium,
                                    color = AppStyle.textColor
                                )
                            }
                        }, onClick = {
                            selectedYear = theYear
                            expandedYear = false
                        })
                    }
                }

            }
        }
        if (totalOfWithdrawalAmount >= 0.0 || totalOfDepositAmount >= 0.0) {
            Spacer(Modifier.height(12.dp))
            HorizontalDivider(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp), color = AppStyle.cardColor
            )
            Spacer(Modifier.height(12.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppStyle.cardColor
                )
            ) {
                //` here we show the remaining amount :
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 18.dp),
                    horizontalArrangement = Arrangement.Absolute.Center
                ) {
                    Text(
                        text = stringResource(R.string.total_amount),
                        color = AppStyle.textColor2,
                        fontFamily = readexpro_medium,
                    )
                    Text(
                        text = " : ",
                        color = red,
                        fontFamily = readexpro_medium,
                    )
                    Text(
                        text = "${totalOfDepositAmount - totalOfWithdrawalAmount}",
                        color = AppStyle.textColor2,
                        fontFamily = readexpro_bold,
                        letterSpacing = 2.50.sp
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 1.dp, shape = RoundedCornerShape(8.dp), brush = Brush.sweepGradient(
                            colors = AppStyle.listOfBrushColor
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //` here we show user the deposit in this month :
                items(depositMonthsData) {
                    Spacer(Modifier.height(12.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = AppStyle.cardColor
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp, vertical = 8.dp),

                            ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {

                                Text(
                                    text = " ${stringResource(R.string.day)} : ${it.day}",
                                    fontFamily = readexpro_medium,
                                    letterSpacing = 2.sp,
                                    color = AppStyle.textColor2
                                )
                                Spacer(modifier = Modifier.width(18.dp))
                                Image(
                                    modifier = Modifier.size(28.dp),
                                    painter = painterResource(it.depositIcon),
                                    contentDescription = "withdrawal icons",
                                )
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.deposit),
                                    color = AppStyle.textColor2,
                                    fontFamily = readexpro_medium,
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = it.depositAmount.toString(),
                                    color = AppStyle.textColor2,
                                    fontFamily = readexpro_bold,
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.category),
                                    color = AppStyle.textColor2,
                                    fontFamily = readexpro_medium,
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = it.depositType,
                                    color = AppStyle.textColor2,
                                    fontFamily = readexpro_bold,
                                )
                            }
                        }
                    }
                }
                //` here we shiw user the withdrawal in this month :
                items(withdrawalMonthsData) {
                    Spacer(Modifier.height(12.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = AppStyle.cardColor
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp, vertical = 8.dp),

                            ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {

                                Text(
                                    text = " ${stringResource(R.string.day)} : ${it.day}",
                                    fontFamily = readexpro_medium,
                                    letterSpacing = 2.sp,
                                    color = AppStyle.textColor2
                                )
                                Spacer(modifier = Modifier.width(18.dp))
                                Image(
                                    modifier = Modifier.size(28.dp),
                                    painter = painterResource(it.withdrawalIcon),
                                    contentDescription = "withdrawal icons",
                                )
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.withdrawal),
                                    color = AppStyle.textColor2,
                                    fontFamily = readexpro_medium,
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = it.amount.toString(),
                                    color = AppStyle.textColor2,
                                    fontFamily = readexpro_bold,
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.category),
                                    color = AppStyle.textColor2,
                                    fontFamily = readexpro_medium,
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = it.type,
                                    color = AppStyle.textColor2,
                                    fontFamily = readexpro_bold,
                                )
                            }
                        }
                    }
                }
            }
            //` here we show to usere that is no data on this month :
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 28.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = AppStyle.cardColor
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 2.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = stringResource(R.string.there_is_no_data_of_this_month),
                            color = AppStyle.textColor2,
                            fontSize = 18.sp,
                            fontFamily = readexpro_medium
                        )
                    }
                }
            }
        }
    }
}



