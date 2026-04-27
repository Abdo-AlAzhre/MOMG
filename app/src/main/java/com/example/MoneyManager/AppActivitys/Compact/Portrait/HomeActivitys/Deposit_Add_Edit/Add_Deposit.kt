package com.example.MoneyManager.AppActivitys.Compact.Portrait.HomeActivitys.Deposit_Add_Edit

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.example.MoneyManager.Model.All_Withdrawal_data.messages_ofToast.MessagesOfToasts
import com.example.MoneyManager.ViewModels.DepositDataViewModel.DepositDataViewModel
import com.example.MoneyManager.ViewModels.DepositType_VM.DepositTypeViewModel
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
import readexpro_medium
import java.util.Calendar

@Composable
fun Add_Deposit(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding(),
        bottomBar = {
            BannerADS(modifier = Modifier)
        }
    ) { paddingValues ->
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.add_edit_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        DepositHome(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            navController
        )
    }
}


private @Composable
fun DepositHome(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val calendar = Calendar.getInstance()
    calendar.get(Calendar.MONTH)
    //` this is the Context of this Activity :
    val context = LocalContext.current
    //` this variable is fore use type deposit ViewModel :
    val depositTypeViewModel: DepositTypeViewModel = viewModel()
    //` this variable to get types deposit data from Realm :
    val newDepositTypes by depositTypeViewModel.depositTypesFlow.collectAsState(initial = emptyList())
    var getType by rememberSaveable { mutableStateOf("") }
    //` thia variable to use deposit datas viewModel:
    val depositDataViewModel: DepositDataViewModel = viewModel()

    //` this variable to get data from data picker :
    var day by rememberSaveable { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }
    var month by rememberSaveable { mutableStateOf(calendar.get(Calendar.MONTH) + 1) }
    var year by rememberSaveable { mutableStateOf(calendar.get(Calendar.YEAR)) }

    var depositAmount by rememberSaveable { mutableStateOf("") }
    var depositTypes by rememberSaveable { mutableStateOf("") }
    var depositDescription by rememberSaveable { mutableStateOf("") }

    var showTypesMenu by rememberSaveable { mutableStateOf(false) }
    var showDataPicker by rememberSaveable { mutableStateOf(false) }


    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            SaveButton(
                modifier = Modifier, onTheClick = {
                    val amountValue = depositAmount.toDoubleOrNull()

                    if (amountValue != null && depositTypes.isNotEmpty() && amountValue > 0.0 && day > 0) {
                        //3 here we chicking if this type is in list or not  , if it is new type we will add it else we will not :
                        newDepositTypes.forEach { oldType ->
                            getType = oldType.depositType
                        }
                        if (depositTypes != getType) {
                            depositTypeViewModel.addDepositType(
                                context = context,
                                name = depositTypes
                            )
                        }
                        //3 here we add the new  data :
                        depositDataViewModel.addDepositData(
                            context = context,
                            amount = amountValue,
                            description = depositDescription,
                            type = depositTypes,
                            day = day,
                            month = month,
                            year = year,
                        )
                        navController.navigate("Compact_home") {
                            popUpTo("Compact_add_deposit") {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }

                    } else if (amountValue == null) {
                        depositTypeViewModel.showToast(
                            context = context,
                            message = MessagesOfToasts.make_sure_the_amount_is_in_numbers_only
                        )
                    } else if (depositTypes.isEmpty()) {
                        depositTypeViewModel.showToast(
                            context = context,
                            message = MessagesOfToasts.you_forgot_to_add_category
                        )
                    } else if (day <= 0) {
                        depositTypeViewModel.showToast(
                            context = context,
                            message = MessagesOfToasts.dont_forget_date
                        )
                    } else {
                        depositTypeViewModel.showToast(
                            context, MessagesOfToasts.amount_must_be_more_then_0
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.back_ground1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //` in this row we have TextFild to add deposit types and amounts :
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                //` this fore  enter the amount of the deposet :
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = depositAmount,
                    onValueChange = { depositAmount = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = {
                        Text(
                            text = stringResource(R.string.deposit), color = AppStyle.outLinText
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
                        textAlign = TextAlign.Right,
                        color = AppStyle.outLinText,
                    ),
                    maxLines = 1,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = black_blue,
                        unfocusedBorderColor = sky_blue,
                        cursorColor = blue_green,
                    )
                )
                //`this to add the deposet type :
                Box(
                    modifier = Modifier.weight(1f),
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showTypesMenu = true
                            },
                        value = depositTypes,
                        onValueChange = {
                            if (it.length <= 18) {
                                depositTypes = it
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        enabled = true,
                        label = {
                            Text(
                                text = "${stringResource(R.string.category)}:${depositTypes.length}/18",
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
                        newDepositTypes.forEach { theDepositTypes ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,

                                        ) {
                                        Text(
                                            text = theDepositTypes.depositType,
                                            fontSize = 12.sp,
                                            fontFamily = readexpro_medium,
                                            color = AppStyle.textColor
                                        )
                                    }
                                },
                                onClick = {
                                    if ((theDepositTypes.depositType).isNotEmpty()) {
                                        depositTypes = theDepositTypes.depositType
                                        showTypesMenu = false
                                    }
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
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
                if (day <= 0) {
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
                        text = "$day/$month/$year",
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

                            day = calendar.get(Calendar.DAY_OF_MONTH)
                            month = calendar.get(Calendar.MONTH) + 1
                            year = calendar.get(Calendar.YEAR)


                        },
                        onDismissButton = {
                            showDataPicker = false

                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            //` here we get descrription from the user :
            TextField(
                modifier = Modifier.fillMaxSize(),
                value = depositDescription,
                onValueChange = { depositDescription = it },
                placeholder = {
                    Text(
                        text = stringResource(R.string.description),
                        color = AppStyle.textColor2,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize()
                    )
                },
                textStyle = TextStyle(
                    fontSize = 20.sp, textAlign = TextAlign.Right, color = AppStyle.textColor2
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = AppStyle.outLinBackground,
                    unfocusedContainerColor = AppStyle.outLinBackground,
                    cursorColor = blue_green
                ),
                shape = RoundedCornerShape(12.dp),
            )

        }
    }
}

//` this button fore save deposit types and  || the other items :
@Composable
private fun SaveButton(
    modifier: Modifier = Modifier,
    onTheClick: () -> Unit,
) {
    val context = LocalContext.current

    Button(
        modifier = modifier
            .alpha(0.90f)
            .padding(horizontal = 18.dp, vertical = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppStyle.floatButtonColor
        ),
        onClick = {
            ImageADS(context = context)//4 here we add image ads \\
            onTheClick()
        },
    ) {
        Row(
            modifier = Modifier.padding(2.50.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_check_24),
                contentDescription = null,
                tint = AppStyle.iconButtonColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.save_item),
                fontSize = 16.sp,
                color = AppStyle.textColor2
            )
        }
    }
}

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
