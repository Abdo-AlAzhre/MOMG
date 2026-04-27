@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.example.xmltools.AppActivitys.Compact.Portrait.HomeActivitys.FirstActivity


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.xmltools.AppADS.BannerADS
import com.example.xmltools.AppADS.ImageADS
import com.money.trackpay.R
import com.example.xmltools.ViewModels.Guest_VM.GuestViewModel
import com.example.xmltools.ui.theme.AppStyle
import com.example.xmltools.ui.theme.black
import com.example.xmltools.ui.theme.blue
import com.example.xmltools.ui.theme.blue_green
import com.example.xmltools.ui.theme.red
import com.example.xmltools.ui.theme.soft_blue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import readexpro_bold
import readexpro_medium

@Composable
fun FirstActivity(navController: NavHostController) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding(), bottomBar = {
            First_bottom()
        }

    ) { paddingValues ->

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.ferst_background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        FirstActivityBody(
            navController,
            modifier = Modifier.padding(paddingValues),

            )
    }
}

@Composable
private fun FirstActivityBody(
    navController: NavHostController,
    modifier: Modifier = Modifier,

    ) {
    var googleSelected by rememberSaveable { mutableStateOf(false) }
    val gustViewModel: GuestViewModel = viewModel()
    var showWarningMessage by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val buttonWeight by animateDpAsState(
        targetValue = if (googleSelected) {
            22.dp
        } else {
            0.dp
        }, animationSpec = MaterialTheme.motionScheme.slowSpatialSpec()
    )

    val context = LocalContext.current



    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.Right,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.height(220.dp), verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "M",
                    color = black,
                    fontFamily = readexpro_bold,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "O",
                    color = black,
                    fontFamily = readexpro_bold,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "M",
                    color = black,
                    fontFamily = readexpro_bold,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "A",
                    color = black,
                    fontFamily = readexpro_bold,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )

            }
            Spacer(modifier = Modifier.width(22.dp))
            Row(
                modifier = Modifier
                    .padding(top = 22.dp)
                    .border(
                        width = 1.dp,
                        brush = Brush.sweepGradient(colors = listOf(blue, blue_green, red)),
                        shape = RoundedCornerShape(200.dp),
                    ),

                ) {
                Lottie_Animation()
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            if (showWarningMessage) {

                ShowWarningMessage(

                    onConfirm = { chek ->
                        if (chek) {
                            gustViewModel.saveGuestState(true)
                            navController.navigate("Compact_home") {
                                popUpTo("Compact_first") {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    },
                    onCancel = {
                        showWarningMessage = false
                    },
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(R.string.google_login_title),
                    color = black,
                    fontFamily = readexpro_medium,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(42.dp))
                Button(
                    modifier = Modifier.alpha(1f), onClick = {
                        googleSelected = true
                        scope.launch {
                            delay(500)
                            showWarningMessage = true
                        }
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = AppStyle.buttonColor
                    )
                ) {
                    Text(
                        text = stringResource(R.string.google_login), color = AppStyle.outLinText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(R.drawable.baseline_account_circle_24),
                        contentDescription = "google icons",
                        modifier = Modifier.size(buttonWeight),
                        tint = red
                    )
                }
            }


        }
    }
}


@Composable
fun First_bottom(modifier: Modifier = Modifier) {
    BannerADS(
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth()
    )//4 here we add image ads \\
}


@Composable
private fun ShowWarningMessage(
    onConfirm: (chek: Boolean) -> Unit, onCancel: () -> Unit
) {
    var checkIsDone by remember { mutableStateOf(false) }
    val context = LocalContext.current
    AlertDialog(
        title = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.warning_title),
                color = red,
                fontFamily = readexpro_bold,

                )
        }
    }, text = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.warning_message),
                textAlign = TextAlign.Center,
                color = AppStyle.textColor2,
                fontSize = 20.sp,
                lineHeight = 30.sp
            )
            Spacer(modifier = Modifier.height(0.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = checkIsDone,
                    onCheckedChange = { checkIsDone = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = soft_blue
                    ),

                    )
                Text(
                    text = stringResource(R.string.are_you_shore),
                    color = AppStyle.textColor2,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }, onDismissRequest = {}, confirmButton = {}, dismissButton = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 2.dp)
        ) {
            Button(
                modifier = Modifier.align(Alignment.CenterStart), onClick = {
                    ImageADS(context = context)//4 here we add image ads \\
                    onCancel()
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = AppStyle.buttonColor
                )
            ) {
                Text(text = stringResource(R.string.cancel), color = AppStyle.outLinText)
            }
            Button(
                modifier = Modifier.align(Alignment.CenterEnd), onClick = {
                    ImageADS(context = context)//4 here we add image ads \\
                    onConfirm(checkIsDone)
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = AppStyle.buttonColor
                ), enabled = checkIsDone
            ) {
                Text(text = stringResource(R.string.confirm), color = AppStyle.outLinText)
            }
        }
    }, shape = RoundedCornerShape(12.dp), containerColor = AppStyle.alertDialogColor
    )
}


@Composable
fun Lottie_Animation(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animaton_note))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        speed = 0.7f,
        isPlaying = true,
        restartOnPlay = false,
        clipSpec = LottieClipSpec.Progress(0f, 1f),
    )

    LottieAnimation(
        modifier = modifier.size(width = 248.dp, height = 380.dp),
        composition = composition,
        progress = { progress },
        contentScale = ContentScale.Crop,

        )
}


