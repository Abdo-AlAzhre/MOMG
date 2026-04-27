@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.example.MoneyManager.AppActivitys.Compact.Portrait.HomeActivitys.Monthly_Setting

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cairo_medium
import com.example.MoneyManager.AppADS.BannerADS
import com.example.MoneyManager.AppADS.ImageADS
import com.example.MoneyManager.ViewModels.DepositDataViewModel.DepositDataViewModel
import com.example.MoneyManager.ViewModels.DepositType_VM.DepositTypeViewModel
import com.example.MoneyManager.ViewModels.WithdrawalType_VM.WithdrawalTypeViewModel
import com.example.MoneyManager.ViewModels.Withdrawal_data_VM.WithdrawalDataViewModel
import com.example.MoneyManager.ui.theme.AppStyle
import com.example.MoneyManager.ui.theme.Change_Style
import com.example.MoneyManager.ui.theme.black
import com.example.MoneyManager.ui.theme.blue_green
import com.example.MoneyManager.ui.theme.crem_color
import com.example.MoneyManager.ui.theme.darkRed
import com.example.MoneyManager.ui.theme.green
import com.money.trackpay.R
import readexpro_medium

@Composable
fun Compact_Setting(navController: NavHostController) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .navigationBarsPadding()
            .background(crem_color), bottomBar = {
            BannerADS(modifier = Modifier)
        }) { innerpadding ->
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.ferst_background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Compact_SettingBody(modifier = Modifier.padding(innerpadding), navController)

    }
}


@Composable
private fun Compact_SettingBody(modifier: Modifier = Modifier, navController: NavHostController) {

    val context = LocalContext.current
    val withdrawalDataViewModel: WithdrawalDataViewModel = viewModel()
    val withdrawalTypeViewModel: WithdrawalTypeViewModel = viewModel()
    val depositTypeViewModel: DepositTypeViewModel = viewModel()
    val depositDataViewModel: DepositDataViewModel = viewModel()

    val depositTypes by depositTypeViewModel.depositTypesFlow.collectAsState(initial = emptyList())
    val withdrawalTypes by withdrawalTypeViewModel.withdrawalTypesFlow.collectAsState(initial = emptyList())

    var changeReceiver by remember { mutableStateOf(false) }
    var changeBackUp by remember { mutableStateOf(false) }
    var showProgress by remember { mutableStateOf(false) }
    var showCategoryList by remember { mutableStateOf(false) }


    val changeArrow by animateFloatAsState(
        targetValue = if (showCategoryList) 180f else 0f,
        animationSpec = MaterialTheme.motionScheme.slowEffectsSpec()
    )
    val animateOfCategoryItem by animateFloatAsState(
        targetValue = if (showCategoryList) 1f else 0f,
        animationSpec = MaterialTheme.motionScheme.slowEffectsSpec()
    )
    val weightOfReceiver by animateDpAsState(
        targetValue = if (changeReceiver) 20.dp else 0.dp,
        animationSpec = MaterialTheme.motionScheme.slowSpatialSpec()
    )
    val weightOfBackUp by animateDpAsState(
        targetValue = if (changeBackUp) 20.dp else 0.dp,
        animationSpec = MaterialTheme.motionScheme.slowSpatialSpec()
    )


    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {

        if (showProgress) {
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            CircularProgressIndicator(color = blue_green, modifier = Modifier.size(30.dp))

        }
        Spacer(modifier = Modifier.padding(vertical = 12.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Center
            ) {

                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Image(
                    modifier = Modifier.size(120.dp),
                    painter = painterResource(R.drawable.app_icons),
                    contentDescription = "",

                    )

            }
            Spacer(modifier = Modifier.padding(horizontal = 6.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.vertion),
                textAlign = TextAlign.Center,
                color = AppStyle.textColor2,
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .border(
                    width = 0.1.dp, color = black, shape = RoundedCornerShape(12.dp)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //~ this button to get data and recover it :
            Button(
                modifier = Modifier.padding(vertical = 12.dp), onClick = {
                    ImageADS(context = context)//4 here we add image ads \\
                    changeReceiver = true

                    depositDataViewModel.restoreDepositData(context)

                    withdrawalDataViewModel.restoreWithdrawalData(
                        context = context,
                        showProgress = { showProgress = true },
                        hiedProgress = { showProgress = false },
                    )
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = AppStyle.buttonColor
                )
            ) {
                Text(
                    text = stringResource(R.string.recover_data),
                    color = AppStyle.textButtonColor,
                    fontFamily = readexpro_medium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    modifier = Modifier.width(0.dp + weightOfReceiver),
                    painter = painterResource(R.drawable.baseline_refresh_24),
                    contentDescription = "refresh data",
                    tint = black,

                    )
            }
            //~ this button to save data in anuther dataBase :
            Button(
                modifier = Modifier.padding(vertical = 12.dp), onClick = {
                    ImageADS(context = context)//4 here we add image ads \\
                    changeBackUp = true

                    depositDataViewModel.bacupDepositData(context)

                    withdrawalDataViewModel.backupWithdrawalData(
                        context = context,
                        showProgress = { showProgress = true },
                        hiedProgress = { showProgress = false },
                    )
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = AppStyle.buttonColor
                )
            ) {
                Text(
                    text = stringResource(R.string.save_data),
                    color = AppStyle.textButtonColor,
                    fontFamily = readexpro_medium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    modifier = Modifier.width(0.dp + weightOfBackUp),
                    painter = painterResource(R.drawable.baseline_save_as_24),
                    contentDescription = "save data",
                    tint = black,

                    )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        //~ here we show to user category :
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .border(
                    width = 0.1.dp, color = black, shape = RoundedCornerShape(12.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                modifier = Modifier, onClick = {
                    showCategoryList = !showCategoryList
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = AppStyle.buttonColor
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text("Category Edit", color = AppStyle.textColor2)
                    Icon(
                        modifier = Modifier.graphicsLayer {
                            this.rotationZ = changeArrow
                        },
                        painter = painterResource(R.drawable.arrow_drop_down),
                        contentDescription = null,
                        tint = green
                    )
                }
            }
            if (showCategoryList) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            this.scaleX = animateOfCategoryItem
                            this.scaleY = animateOfCategoryItem
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    //4 this lsit for show user the withdrawal Categorys :
                    items(withdrawalTypes) { withdrawalTypes ->
                        Row(
                            modifier = Modifier
                                .graphicsLayer {
                                    this.scaleX = animateOfCategoryItem
                                    this.scaleY = animateOfCategoryItem
                                }
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                modifier = Modifier.weight(3f),
                                text = withdrawalTypes.WithdrawalType,
                                color = AppStyle.textColor2,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontFamily = cairo_medium,
                            )
                            IconButton(
                                modifier = Modifier.weight(1f), onClick = {
                                    withdrawalTypeViewModel.deleteType(context, withdrawalTypes.id)
                                }) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_delete_24),
                                    contentDescription = null,
                                    tint = darkRed
                                )
                            }
                        }
                        HorizontalDivider(modifier = Modifier.fillMaxWidth())
                    }
                    //1 this lsit for show user the deposit Categorys :
                    items(depositTypes) { depositTypes ->
                        Row(
                            modifier = Modifier
                                .graphicsLayer {
                                    this.scaleX = animateOfCategoryItem
                                    this.scaleY = animateOfCategoryItem
                                }
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {

                            Text(
                                modifier = Modifier.weight(3f),
                                text = depositTypes.depositType,
                                color = AppStyle.textColor2,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontFamily = cairo_medium,
                            )
                            IconButton(
                                modifier = Modifier.weight(1f), onClick = {
                                    depositTypeViewModel.deleteDepositType(context, depositTypes.id)
                                }) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_delete_24),
                                    contentDescription = null,
                                    tint = darkRed
                                )
                            }
                        }
                        HorizontalDivider(modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SettingPreview(modifier: Modifier = Modifier) {
    Change_Style()
    Compact_Setting(navController = rememberNavController())
}


