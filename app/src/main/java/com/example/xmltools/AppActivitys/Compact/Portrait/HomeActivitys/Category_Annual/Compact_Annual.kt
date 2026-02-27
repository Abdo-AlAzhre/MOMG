package com.example.xmltools.AppActivitys.Compact.Portrait.HomeActivitys

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint

import android.graphics.Typeface
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import cairo_medium
import com.example.xmltools.Model.All_Withdrawal_data.listOfYears
import com.example.xmltools.ViewModels.Annual_Monthly_VM.AnnualViewModel
import com.example.xmltools.ui.theme.AppStyle
import com.example.xmltools.ui.theme.black
import com.example.xmltools.ui.theme.blue_shadow
import com.example.xmltools.ui.theme.dark_blue_shadow
import com.money.trackpay.R
import readexpro_bold
import readexpro_medium
import java.util.Calendar

@Composable
fun Compact_Annual(navController: NavHostController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding()

    ) { innerPadding ->
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.annual_background),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        AnnualExpensesChartScreen(modifier = Modifier.padding(innerPadding))

    }
}

@Composable
fun AnnualExpensesChartScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: AnnualViewModel = viewModel()
    val calendar = Calendar.getInstance()

    var selectedYear by remember { mutableIntStateOf(calendar.get(Calendar.YEAR)) }
    var expandedYear by remember { mutableStateOf(false) }

    //1 here we gat withdrawal ;
    val withdrawalDataList by viewModel.getWithdrawalDataByYear(selectedYear)
        .collectAsState(initial = emptyList())
    val withdrawalTotalAmount = viewModel.getWithdrawalYearTotal(withdrawalDataList)
    val withdrawalMonthlyTotals = viewModel.getWithdrawalMonthlyTotals(withdrawalDataList)
    //2 here we gat deposit :
    val depositDataList by viewModel.getDepositDataByYear(selectedYear)
        .collectAsState(initial = emptyList())
    val depositTotalAmount = viewModel.getDepositYearTotal(depositDataList)
    val depositMonthlyTotals = viewModel.getDepositMonthlyTotals(depositDataList)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        //~ here we show usere that if there is data on this year or not
        if (depositTotalAmount == 0.0 && withdrawalTotalAmount == 0.0) {
            Text(
                text = stringResource(R.string.there_is_no_data_of_this_year),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp),
                color = AppStyle.outLinText,
                fontFamily = readexpro_bold,
            )
        } else {
            Text(
                text = "${stringResource(R.string.expenses_of_the_year)} $selectedYear",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp),
                color = AppStyle.outLinText,
                fontFamily = readexpro_bold,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.2f)
                .border(
                    width = 1.dp,
                    color = black,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 4.dp),

            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //~ here we show user waht we have now after withdrawal and deposit :
                Row(
                    modifier = Modifier.weight(1.50f),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${stringResource(R.string.total_amount)} : ",
                        color = AppStyle.outLinText,
                        fontFamily = readexpro_medium,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "${depositTotalAmount - withdrawalTotalAmount}",
                        color = AppStyle.outLinText,
                        fontFamily = readexpro_bold,
                        fontSize = 16.sp,
                    )

                }
                //~ here usere enter that year that he want to search for :
                Box(modifier = Modifier.weight(1f)) {
                    TextField(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                brush = Brush.sweepGradient(
                                    colors = AppStyle.listOfBrushColor
                                ),
                                shape = RoundedCornerShape(8.dp)
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
                                color = AppStyle.outLinText
                            )
                        },
                        textStyle = TextStyle(color = AppStyle.outLinText),
                        trailingIcon = {
                            IconButton(
                                onClick = { expandedYear = true }) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_date_range_24),
                                    contentDescription = "",
                                    tint = black,
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = dark_blue_shadow,
                            unfocusedContainerColor = blue_shadow,
                            disabledContainerColor = blue_shadow
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    DropdownMenu(
                        modifier = Modifier.height(350.dp),
                        expanded = expandedYear,
                        onDismissRequest = { expandedYear = false },
                        containerColor = dark_blue_shadow
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
            HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp))
            Spacer(Modifier.height(8.dp))
            //~ here we show user how match money he deposit in this year :
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
            ) {
                Text(
                    text = stringResource(R.string.total_deposit_in_this_year),
                    color = AppStyle.textColor2,
                    fontFamily = readexpro_bold,
                    fontSize = 14.sp,
                )
                Text(
                    text = " : $depositTotalAmount",
                    color = AppStyle.textColor2,
                    fontFamily = readexpro_bold,
                    fontSize = 16.sp,
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(5f)
                .border(
                    width = 1.dp,
                    color = black,
                    shape = RoundedCornerShape(
                        topStart = 12.dp,
                        topEnd = 12.dp,
                        bottomStart = 28.dp,
                        bottomEnd = 28.dp
                    )
                )
        ) {
            //~ here we toalt user that he did`nt do any withdrawal in this year
            if (withdrawalMonthlyTotals.isEmpty()) {
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
                                        text = stringResource(R.string.no_withdrawal_in_this_year),
                                        color = AppStyle.textColor2,
                                        fontFamily = readexpro_bold,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }

                        }
                    }
                }
            }
            //~ here we show the withdrawal if it is not empty :
            else {
                Text(
                    modifier = Modifier.align(Alignment.TopCenter),
                    text = stringResource(R.string.total_withdrawal_in_this_year),
                    color = AppStyle.textColor2,
                    fontFamily = readexpro_bold,
                    textAlign = TextAlign.Center,
                )
                AnimatedAnnualBarChart(
                    withdrawalMonthlyTotals,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}

//4 this fun to show the withdrawal animation :
@SuppressLint("UseKtx")
@Composable
fun AnimatedAnnualBarChart(
    monthlyData: List<Pair<Int, Double>>,
    modifier: Modifier = Modifier
) {
    val monthsNames = listOf(
        "1", "2", "3", "4", "5", "6",
        "7", "8", "9", "10", "11", "12"
    )
    val darkTheme: Boolean = isSystemInDarkTheme()
    var animationProgress by remember { mutableStateOf(0f) }
    // `تشغيل الأنيميشن عند ظهور الرسم البياني
    LaunchedEffect(Unit) {
        animate(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = tween(durationMillis = 15050, easing = FastOutSlowInEasing)
        ) { value, _ ->
            animationProgress = value
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp)
            .padding(12.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val maxVal = (monthlyData.maxOfOrNull { it.second } ?: 1.0)
            val barWidth = size.width / (monthlyData.size * 1.5f)
            val spacing = barWidth / 2

            monthlyData.forEachIndexed { index, (month, amount) ->
                val barHeight = ((amount / maxVal) * (size.height - 80f) * animationProgress)
                val left = index * (barWidth + spacing)
                val top = size.height - barHeight - 50f
                //` تدرج لوني أنيق للأعمدة
                drawRoundRect(
                    brush = Brush.verticalGradient(
                        colors = AppStyle.bridgeColor
                    ),
                    topLeft = Offset(left, top.toFloat()),
                    size = Size(barWidth, barHeight.toFloat()),
                    cornerRadius = CornerRadius(16f, 16f)
                )
                // `اسم الشهر أسفل العمود
                val monthName = monthsNames.getOrNull(month - 1) ?: "?"
                drawContext.canvas.nativeCanvas.drawText(
                    monthName,
                    left + barWidth / 2,
                    size.height - 10f,
                    Paint().apply {
                        textSize = 42f
                        color =
                            if (darkTheme) Color.parseColor("#000000") else Color.parseColor(
                                "#000000"
                            )
//                        Paint.setTypeface = Typeface.create(
//                            "sans-serif-medium",
//                            Typeface.BOLD
//                        )
                        textAlign = Paint.Align.CENTER
                    }
                )
                // `المبلغ فوق العمود
                if (amount > 0) {
                    drawContext.canvas.nativeCanvas.drawText(
                        amount.toInt().toString(),
                        left + barWidth / 2,
                        top.toFloat() - 10f,
                        Paint().apply {
                            textSize = 38f
                            color =
                                if (darkTheme) Color.parseColor("#000000") else Color.parseColor(
                                    "#000000"
                                )
//                            Paint.setTypeface = Typeface.create(
//                                "sans-serif",
//                                Typeface.BOLD
//                            )
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }
        }
    }
}
