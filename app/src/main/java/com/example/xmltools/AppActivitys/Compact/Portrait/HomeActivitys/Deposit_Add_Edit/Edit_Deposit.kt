package com.example.xmltools.AppActivitys.Compact.Portrait.HomeActivitys.Deposit_Add_Edit

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
import com.example.xmltools.Model.All_Deposit_data.Deposit_data
import com.example.xmltools.Model.All_Withdrawal_data.messages_ofToast.MessagesOfToasts
import com.example.xmltools.ViewModels.DepositDataViewModel.DepositDataViewModel
import com.example.xmltools.ViewModels.DepositType_VM.DepositTypeViewModel
import com.example.xmltools.creating_realm_data.Realm_Deposit_LocalData.SaveDepositData
import com.example.xmltools.ui.theme.AppStyle
import com.example.xmltools.ui.theme.black
import com.example.xmltools.ui.theme.black_blue
import com.example.xmltools.ui.theme.blueLight
import com.example.xmltools.ui.theme.blue_green
import com.example.xmltools.ui.theme.dark_blue_shadow
import com.example.xmltools.ui.theme.red
import com.example.xmltools.ui.theme.skyBlue
import com.example.xmltools.ui.theme.sky_blue
import com.example.xmltools.ui.theme.white
import com.money.trackpay.R
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import org.mongodb.kbson.ObjectId
import readexpro_medium
import java.util.Calendar

@Composable
fun Edit_Deposit(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    id: String
) {

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding(),
    ) { paddingValues ->
        Edit_Deposit_Body(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            id,
            navController
        )
    }
}

@Composable
private fun Edit_Deposit_Body(
    modifier: Modifier = Modifier,
    id: String,
    navController: NavHostController
) {

    val context = LocalContext.current
    //~ here we have get all realm :
    val realm: Realm = remember { SaveDepositData.realmDepositData }
    val allData = remember {
        realm.query<Deposit_data>("id == $0", ObjectId(id)).first().find()
    }
    val depositDataViewModel: DepositDataViewModel = viewModel()
    val depositTypeViewModel: DepositTypeViewModel = viewModel()
    //~ here we get old deposit types :
    val oldDepositTypes by depositTypeViewModel.depositTypesFlow.collectAsState(initial = emptyList())

    //~ here we get all data from Deposit data :
    var depositAmount by rememberSaveable { mutableStateOf(allData!!.depositAmount.toString()) }

    var days by rememberSaveable { mutableStateOf(allData!!.day) }
    var months by rememberSaveable { mutableStateOf(allData!!.month) }
    var years by rememberSaveable { mutableStateOf(allData!!.year) }

    var item by rememberSaveable { mutableStateOf(allData!!.depositDescriptor) }
    var depositTypes by rememberSaveable { mutableStateOf(allData!!.depositType) }


    var showDataPicker by rememberSaveable { mutableStateOf(false) }
    var showTypesMenu by rememberSaveable { mutableStateOf(false) }


    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            SaveData(
                modifier = Modifier,
                onClick = {
                    val amountValue = depositAmount.toDoubleOrNull()

                    if (depositTypes.isNotEmpty() && amountValue != null && days > 0 && depositTypes != "إضافة شئ اخر + ") {

                        depositTypeViewModel.addDepositType(context, depositTypes)
                        depositDataViewModel.editDepositData(
                            context = context,
                            id = allData!!.id,
                            amount = amountValue,
                            description = item,
                            type = depositTypes,
                            day = days,
                            month = months,
                            year = years
                        )
                        depositDataViewModel.showToast(
                            context,
                            MessagesOfToasts.successfully_saving
                        )
                        navController.navigate("Compact_home") {
                            popUpTo("edit_deposit") {
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
            painter = painterResource(R.drawable.add_edit_background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                //~here we add withdrawal amount :
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = depositAmount,
                    onValueChange = {
                        depositAmount = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = {
                        Text(
                            text = stringResource(R.string.deposit),
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
                //~here we add the new type of withdrawal :
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
                        maxLines = 1,
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
                        oldDepositTypes.forEach { theTypes ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,

                                        ) {
                                        Text(
                                            text = theTypes.depositType,
                                            fontSize = 12.sp,
                                            fontFamily = readexpro_medium,
                                            color = AppStyle.textColor
                                        )
                                    }
                                },
                                onClick = {
                                    if ((theTypes.depositType).isNotEmpty()) {
                                        depositTypes = theTypes.depositType
                                        showTypesMenu = false
                                    }
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
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
    }
}


//2 this button to save new data on the old deposit Data location :
@Composable
private fun SaveData(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
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
        onDismissRequest = {onDismissButton()},
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
                currentYearContentColor=white,
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