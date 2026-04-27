@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.example.MoneyManager.AppActivitys.Compact.Portrait.HomeActivitys.Home_MoreInfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButtonMenu
import androidx.compose.material3.FloatingActionButtonMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import cairo_bold
import cairo_medium
import cairo_regular
import com.example.MoneyManager.AppADS.BannerADS
import com.example.MoneyManager.AppADS.ImageADS
import com.example.MoneyManager.Model.All_Deposit_data.Deposit_data
import com.example.MoneyManager.Model.All_Withdrawal_data.messages_ofToast.MessagesOfToasts
import com.example.MoneyManager.Model.All_Withdrawal_data.messages_ofToast.ModelMoreButtons
import com.example.MoneyManager.ViewModels.DepositDataViewModel.DepositDataViewModel
import com.example.MoneyManager.creating_realm_data.Realm_Deposit_LocalData.SaveDepositData
import com.example.MoneyManager.ui.theme.AppStyle
import com.example.MoneyManager.ui.theme.black
import com.example.MoneyManager.ui.theme.blue_green
import com.example.MoneyManager.ui.theme.green
import com.example.MoneyManager.ui.theme.red
import com.money.trackpay.R
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import org.mongodb.kbson.ObjectId
import readexpro_medium


@Composable
fun Deposit_Information(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    id: String,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding(),
        bottomBar = {
            BannerADS(modifier = Modifier)
        }
    ) { paddingValues ->

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.ferst_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
        DepositInformation_Body(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            navController,
            id
        )
    }

}

private @Composable
fun DepositInformation_Body(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    id: String
) {

    val depositDataViewModel: DepositDataViewModel = viewModel()
    val realm: Realm = remember { SaveDepositData.realmDepositData }
    val getDataById = remember {
        realm.query<Deposit_data>("id == $0", ObjectId(id)).first().find()
    }
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    var amounts by remember { mutableStateOf("") }
    var items by remember { mutableStateOf("") }
    var types by remember { mutableStateOf("") }
    var dates by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    val nonList = listOf<String>(
        ""
    )
    //2 here we add the value of those variable from Realm Deposit Data ;
    if (getDataById != null) {
        amounts = getDataById.depositAmount.toString()
        items = getDataById.depositDescriptor
        types = getDataById.depositType
        dates = "${getDataById.day}/${getDataById.month}/${getDataById.year}"
        id = getDataById.id.toHexString()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FabMenu(
                modifier = Modifier,
                onDelete = { showDialog = true },
                onEdit = { navController.navigate("edit_deposit/${id}") }
            )
        }
    ) { paddingValues ->
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.ferst_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //` show date to user :
            Text(
                text = dates,
                fontSize = 18.sp,
                fontFamily = cairo_bold,
                color = AppStyle.outLinText
            )
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            //` show amount and catigory to user :
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 6.dp)
                            .shadow(
                                elevation = 1.dp,
                            )
                            .border(
                                width = 1.dp,
                                brush = Brush.sweepGradient(
                                    colors = AppStyle.listOfBrushColor
                                ),
                                shape = RoundedCornerShape(1.dp)
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = stringResource(R.string.deposit),
                            fontSize = 18.sp,
                            fontFamily = cairo_medium,
                            color = AppStyle.outLinText
                        )
                        Text(
                            text = " $amounts ",
                            fontSize = 18.sp,
                            fontFamily = readexpro_medium,
                            color = AppStyle.outLinText
                        )
                        Spacer(Modifier.padding(vertical = 2.dp))
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 6.dp)
                            .shadow(
                                elevation = 1.dp,
                            )
                            .border(
                                width = 1.dp,
                                brush = Brush.linearGradient(
                                    colors = AppStyle.listOfBrushColor
                                ),
                                shape = RoundedCornerShape(1.dp)
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            text = stringResource(R.string.category),
                            fontSize = 18.sp,
                            fontFamily = cairo_medium,
                            color = AppStyle.outLinText
                        )
                        Text(
                            text = " $types ",
                            fontSize = 18.sp,
                            fontFamily = readexpro_medium,
                            color = AppStyle.outLinText
                        )
                        Spacer(Modifier.padding(vertical = 2.dp))
                    }
                }
            }
            //` show discription to user :
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                        .border(
                            width = 1.dp,
                            brush = Brush.sweepGradient(
                                colors = AppStyle.listOfBrushColor
                            ),
                            shape = RoundedCornerShape(1.dp)
                        )
                        .shadow(elevation = 1.dp),
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(nonList) {
                            Text(
                                modifier = Modifier
                                    .padding(14.dp)
                                    .fillMaxSize(),
                                text = items,
                                fontSize = 18.sp,
                                fontFamily = readexpro_medium,
                                letterSpacing = 2.sp,
                                color = AppStyle.outLinText,
                                lineHeight = 32.sp
                            )
                        }
                    }
                }
            }
            //` this daialog to delete this item :
            if (showDialog) {
                morInfoDialog(
                    amounts = amounts,
                    types = types,

                    onCansel = {
                        showDialog = false
                    },
                    onConfirm = {
                        getDataById?.let {
                            depositDataViewModel.deleteDepositData(
                                context = context,
                                id = getDataById.id
                            )
                        }
                        depositDataViewModel.showToast(
                            context = context,
                            message = MessagesOfToasts.deleting_successfully
                        )
                        navController.navigate("Compact_home") {
                            popUpTo("edit_deposit") {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    },
                )
            }
        }
    }
}

//` this button to delete or edit this item :
@Composable
private fun FabMenu(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    val fabItems = listOf<ModelMoreButtons>(
        ModelMoreButtons(
            text = stringResource(R.string.edit),
            icon = R.drawable.baseline_edit_24
        ),
        ModelMoreButtons(
            text = stringResource(R.string.delete),
            icon = R.drawable.baseline_delete_24
        ),
    )
    var expanded by remember { mutableStateOf(false) }

    FloatingActionButtonMenu(
        modifier = modifier.alpha(0.85f),
        expanded = expanded,
        button = {
            ToggleFloatingActionButton(
                checked = expanded,
                onCheckedChange = { expanded = it },
                containerColor = { if (expanded) AppStyle.buttonColor else AppStyle.floatButtonColor }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(
                        if (expanded) R.drawable.baseline_keyboard_arrow_down_24 else R.drawable.baseline_keyboard_arrow_up_24
                    ),
                    contentDescription = null,
                    tint = AppStyle.iconButtonColor
                )
            }
        }
    ) {
        fabItems.forEach { infoItems ->
            FloatingActionButtonMenuItem(
                onClick = {
                    when (infoItems.icon) {
                        R.drawable.baseline_delete_24 -> onDelete()
                        R.drawable.baseline_edit_24 -> onEdit()
                    }
                },
                text = {
                    Text(text = infoItems.text, color = AppStyle.textColor2)
                },
                icon = {
                    Icon(
                        painter = painterResource(infoItems.icon),
                        contentDescription = null,
                        tint =
                            when (infoItems.icon) {
                                R.drawable.baseline_delete_24 -> red
                                R.drawable.baseline_edit_24 -> green
                                else -> black
                            }

                    )
                },
                containerColor = AppStyle.buttonColor
            )

        }
    }

}

//~ this daialog to delete this item :
@Composable
private fun morInfoDialog(
    amounts: String,
    types: String,
    onCansel: () -> Unit,
    onConfirm: () -> Unit
) {

    val context = LocalContext.current

    AlertDialog(
        modifier = Modifier.graphicsLayer {

        },
        onDismissRequest = {},
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "  $types",
                    fontSize = 18.sp,
                    fontFamily = cairo_bold,
                    color = AppStyle.textColor4
                )
                Text(
                    text = " : ${stringResource(R.string.category)} ",
                    fontSize = 18.sp,
                    fontFamily = cairo_regular,
                    color = AppStyle.textColor4
                )
            }
        },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = " $amounts ",
                    fontSize = 18.sp,
                    fontFamily = cairo_bold,
                    color = AppStyle.textColor4
                )
                Text(
                    text = " : ${stringResource(R.string.amount)} ",
                    fontSize = 18.sp,
                    fontFamily = cairo_regular,
                    color = AppStyle.textColor4
                )
            }
        },
        confirmButton = {},
        dismissButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        ImageADS(context = context)//4 here we add image ads \\
                        onCansel()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppStyle.buttonColor
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_close_24),
                        contentDescription = "close",
                        tint = blue_green
                    )
                    Text(text = stringResource(R.string.cancel), color = AppStyle.outLinText)
                }
                Button(

                    onClick = {
                        ImageADS(context = context)//4 here we add image ads \\
                        onConfirm()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppStyle.buttonColor
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_delete_24),
                        contentDescription = "Confirm",
                        tint = red
                    )
                    Text(text = stringResource(R.string.confirm), color = AppStyle.outLinText)
                }

            }
        },
        containerColor = AppStyle.alertDialogColor
    )
}