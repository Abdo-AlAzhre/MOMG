package com.example.MoneyManager.AppActivitys.Compact.Portrait.HomeActivitys.Withdrawal_Add_Edit

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import cairo_bold
import cairo_medium
import com.example.MoneyManager.AppADS.BannerADS
import com.example.MoneyManager.AppADS.ImageADS
import com.example.MoneyManager.Model.All_Withdrawal_data.Withdrawal_Data
import com.example.MoneyManager.Model.All_Withdrawal_data.messages_ofToast.MessagesOfToasts
import com.example.MoneyManager.ViewModels.Withdrawal_data_VM.WithdrawalDataViewModel
import com.example.MoneyManager.ViewModels.WithdrawalType_VM.WithdrawalTypeViewModel
import com.example.MoneyManager.creating_realm_data.Realm_Withdrawal_LocalData.SavingWithdrawalData
import com.example.MoneyManager.ui.theme.AppStyle
import com.example.MoneyManager.ui.theme.black
import com.example.MoneyManager.ui.theme.black_blue
import com.example.MoneyManager.ui.theme.blueLight
import com.example.MoneyManager.ui.theme.blue_green
import com.example.MoneyManager.ui.theme.dark_blue_shadow
import com.example.MoneyManager.ui.theme.red
import com.example.MoneyManager.ui.theme.skyBlue
import com.example.MoneyManager.ui.theme.sky_blue
import com.example.MoneyManager.ui.theme.white
import com.money.trackpay.R
import io.realm.kotlin.ext.query
import readexpro_medium
import java.util.Calendar

@Composable
fun Compact_Edit(navController: NavHostController, id: String) {
    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .systemBarsPadding(),
        bottomBar = {
            BannerADS(modifier = Modifier)
        }
    ) { innerpadding ->
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(R.drawable.add_edit_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Compact_EditBody(navController, id, modifier = Modifier.padding(innerpadding))
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun Compact_EditBody(
    navController: NavHostController,
    idd: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    //3 here we get data :
    val realmWithdrawalData = remember { SavingWithdrawalData.realmWithdrawal }
    val allData = remember {
        realmWithdrawalData.query<Withdrawal_Data>(
            "id == $0",
            org.mongodb.kbson.ObjectId(idd)
        ).first().find()
    }
    val withdrawalDataViewModel: WithdrawalDataViewModel = viewModel()

    //3 here we get types :
    val withdrawalTypeViewModel: WithdrawalTypeViewModel = viewModel()
    val oldWithdrawalType by withdrawalTypeViewModel.withdrawalTypesFlow.collectAsState(initial = emptyList())
    var getOldType by rememberSaveable { mutableStateOf("") }

    var withdrawalAmount by rememberSaveable { mutableStateOf(allData!!.amount.toString()) }
    var days by rememberSaveable { mutableStateOf(allData!!.day) }
    var months by rememberSaveable { mutableStateOf(allData!!.month) }
    var years by rememberSaveable { mutableStateOf(allData!!.year) }
    var item by rememberSaveable { mutableStateOf(allData!!.items) }
    var withdrawalTypes by rememberSaveable { mutableStateOf(allData!!.type) }

    var showDataPicker by rememberSaveable { mutableStateOf(false) }
    var showTypesMenu by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(alignment = Alignment.Center)
                .padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                //`here we add withdrawal amount :
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = withdrawalAmount,
                    onValueChange = {
                        withdrawalAmount = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = {
                        Text(
                            text = stringResource(R.string.withdrawal),
                            color = AppStyle.outLinText,
                            fontSize = 12.sp,
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.money),
                            contentDescription = "",
                            tint = AppStyle.iconButtonColor
                        )
                    },

                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = AppStyle.outLinText
                    ),
                    maxLines = 1,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = black_blue,
                        unfocusedBorderColor = sky_blue,
                        cursorColor = blue_green,
                    )
                )
                //`here we add the new type of withdrawal :
                Box(
                    modifier = Modifier.weight(1f),
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showTypesMenu = true
                            },
                        value = withdrawalTypes,
                        maxLines = 1,
                        onValueChange = {
                            if (it.length <= 18) {
                                withdrawalTypes = it
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        enabled = true,
                        label = {
                            Text(
                                text = "${stringResource(R.string.category)}:${withdrawalTypes.length}/18",
                                color = AppStyle.outLinText,
                                fontSize = 10.sp,
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { showTypesMenu = true }) {
                                Icon(
                                    painter = painterResource(R.drawable.menu),
                                    contentDescription = "",
                                    tint = AppStyle.iconButtonColor
                                )
                            }
                        },
                        textStyle = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = cairo_medium,
                            color = AppStyle.outLinText,
                            textAlign = TextAlign.Center,
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = black_blue,
                            unfocusedBorderColor = sky_blue,
                            disabledBorderColor = sky_blue
                        )
                    )
                    //` this is the menu of the lust deposit type must used befor :
                    DropdownMenu(
                        modifier = Modifier.height(350.dp),
                        expanded = showTypesMenu,
                        onDismissRequest = { showTypesMenu = false },
                        containerColor = dark_blue_shadow,
                    ) {
                        oldWithdrawalType.forEach { theTypes ->
                            //` the menu of types :
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,

                                        ) {
                                        Text(
                                            text = theTypes.WithdrawalType,
                                            fontSize = 12.sp,
                                            fontFamily = readexpro_medium,
                                            color = AppStyle.textColor
                                        )
                                    }
                                },
                                onClick = {
                                    if ((theTypes.WithdrawalType).isNotEmpty()) {
                                        withdrawalTypes = theTypes.WithdrawalType
                                        showTypesMenu = false
                                    }
                                }
                            )
                        }
                    }
                }
            }
            //` add days :
            //` here we get days , mounthes , and years :
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = skyBlue,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    onClick = {
                        showDataPicker = true
                    },
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppStyle.buttonColor
                    )
                ) {
                    Text(text = stringResource(R.string.set_data), color = AppStyle.textButtonColor)
                }
                if (days <= 0) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.enter_the_date),
                        textAlign = TextAlign.Center,
                        color = AppStyle.textColor2,
                        letterSpacing = 0.sp,
                        fontSize = 16.sp,
                        fontFamily = cairo_bold
                    )
                } else {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "$days/$months/$years",
                        textAlign = TextAlign.Center,
                        color = AppStyle.textColor2,
                        letterSpacing = 2.sp,
                        fontSize = 18.sp,
                        fontFamily = cairo_bold
                    )
                }

                if (showDataPicker) {
                    DatePickerModel(
                        onConfirmButton = { calendar ->
                            showDataPicker = false

                            days = calendar.get(Calendar.DAY_OF_MONTH)
                            months = calendar.get(Calendar.MONTH) + 1
                            years = calendar.get(Calendar.YEAR)


                        },
                        onDismissButton = {
                            showDataPicker = false

                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            //` here we add the description :
            TextField(
                modifier = Modifier
                    .fillMaxSize(),
                value = item,
                onValueChange = { item = it },
                placeholder = {
                    Text(
                        text = stringResource(R.string.category), color = AppStyle.textColor2,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize()
                    )
                },
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Right,
                    color = AppStyle.textColor2
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = AppStyle.outLinBackground,
                    unfocusedContainerColor = AppStyle.outLinBackground,
                    cursorColor = blue_green
                ),
                shape = RoundedCornerShape(12.dp)
            )


        }
        // ` this button to save the new data on the old data location ;
        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp),
            onClick = {

                ImageADS(context = context)//4 here we add image ads \\

                val amountValue = withdrawalAmount.toDoubleOrNull()

                if (withdrawalTypes.isNotEmpty() && amountValue != null && days > 0 && withdrawalTypes != "إضافة شئ اخر + ") {
                    val withdrawalData = Withdrawal_Data().apply {
                        id = allData!!.id
                        amount = amountValue
                        items = item
                        type = withdrawalTypes
                        day = days
                        month = months
                        year = years
                        date = "$day/$month/$year"
                    }
                    //3 chicking if this type is in tha data we will edit it , else we will add it in new type :
                    oldWithdrawalType.forEach { oldType ->
                        getOldType = oldType.WithdrawalType
                    }
                    if (withdrawalTypes != getOldType) {
                        withdrawalTypeViewModel.addWithdrawalType(context, withdrawalTypes)
                    }
                    //3 adding new data :
                    withdrawalDataViewModel.editData(
                        locateDataBase = withdrawalData,
                        context = context,
                        navController = navController,
                        navTo = "Compact_home",
                    ) {

                    }
                } else if (amountValue == null) {
                    withdrawalDataViewModel.showToast(
                        context = context,
                        message = MessagesOfToasts.make_sure_the_amount_is_in_numbers_only
                    )
                } else if (withdrawalTypes.isEmpty()) {
                    withdrawalDataViewModel.showToast(
                        context = context,
                        message = MessagesOfToasts.you_forgot_to_add_category
                    )
                } else {
                    withdrawalDataViewModel.showToast(
                        context, MessagesOfToasts.amount_must_be_more_then_0
                    )
                }
            },
            shape = RoundedCornerShape(size = 12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppStyle.floatButtonColor
            ),
        ) {
            Row(
                modifier = Modifier.padding(2.50.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_check_24),
                    contentDescription = null,
                    tint = AppStyle.iconButtonColor
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.save_item), fontSize = 16.sp,
                    color = AppStyle.textColor2
                )
            }
        }
    }
}

//2 here we get date from DatePicker of and edit to :
//` this fun to get day , month , and years :
@SuppressLint("DefaultLocale")
@Composable
private fun DatePickerModel(
    modifier: Modifier = Modifier,
    onDismissButton: () -> Unit,
    onConfirmButton: (Calendar) -> Unit,
) {
    val dataPickerState = rememberDatePickerState()
    val calendar = Calendar.getInstance()

    DatePickerDialog(
        modifier = modifier.padding(12.dp),
        shape = RoundedCornerShape(20.dp),
        colors = DatePickerDefaults.colors(
            containerColor = AppStyle.dialogColor
        ),
        onDismissRequest = { onDismissButton() },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = {
                        dataPickerState.selectedDateMillis.let { millis ->
                            if (millis != null) {
                                calendar.timeInMillis = millis
                                onConfirmButton(calendar)
                            }
                        }
                    },
                ) {
                    Text(text = stringResource(R.string.confirm), color = AppStyle.buttonColor)
                }

                TextButton(
                    onClick = {
                        onDismissButton()
                    },
                ) {
                    Text(text = stringResource(R.string.cancel), color = red)
                }
            }
        },
    ) {
        DatePicker(
            state = dataPickerState,
            title = {
                Text(
                    text = stringResource(R.string.enter_the_date),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp,
                    color = AppStyle.textColor2,
                    fontSize = 16.sp,
                    fontFamily = cairo_bold,
                )
            },
            colors = DatePickerDefaults.colors(

                containerColor = AppStyle.alertDialogColor,
                todayDateBorderColor = red,

                yearContentColor = white,
                currentYearContentColor = white,
                selectedYearContentColor = black,
                selectedYearContainerColor = blueLight,

                dayContentColor = white,
                todayContentColor = white,
                selectedDayContentColor = black,
                selectedDayContainerColor = blueLight,

                dateTextFieldColors = TextFieldDefaults.colors(
                    cursorColor = white,
                    focusedTextColor = AppStyle.textColor,
                    unfocusedTextColor = AppStyle.textColor,
                )
            ),

            )
    }
}