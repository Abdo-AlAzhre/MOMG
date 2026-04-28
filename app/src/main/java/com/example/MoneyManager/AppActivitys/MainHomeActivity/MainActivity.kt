package com.money.trackpay.AppActivitys.MainHomeActivity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.MoneyManager.AppActivitys.Nav_Size.ShowScreens
import com.example.MoneyManager.LocalAuth.LocalAuthManager
import com.example.MoneyManager.Model.All_Withdrawal_data.messages_ofToast.MessagesOfToasts
import com.example.MoneyManager.Model.All_Withdrawal_data.Language_Color_Lists.GetLanguage_ColorName
import com.example.MoneyManager.ViewModels.Automatic_Save_VM.BackupViewModel
import com.example.MoneyManager.ViewModels.WithdrawalType_VM.WithdrawalTypeViewModel
import com.example.MoneyManager.ui.theme.Change_Style
import com.example.MoneyManager.ui.theme.XMLToolsTheme
import com.example.MoneyManager.ui.theme.black
import com.example.MoneyManager.ui.theme.blueLight
import com.example.MoneyManager.ui.theme.white
import com.money.trackpay.R

class MainActivity : AppCompatActivity() {

    private val authManager by lazy {
        LocalAuthManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val backupViewModel: BackupViewModel by viewModels()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XMLToolsTheme {

                val authResult by authManager.promptResult.collectAsState(null)
                val enrollLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult(),
                    onResult = {
                        print("result is : $it")
                    }
                )

                LaunchedEffect(authResult) {
                    if (authResult is LocalAuthManager.AuthResult.AuthenticationNotSet) {
                        if (Build.VERSION.SDK_INT >= 30) {
                            val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                                putExtra(
                                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                                )
                            }
                            enrollLauncher.launch(enrollIntent)
                        }
                    }
                }

                Column(
                    modifier = Modifier.fillMaxSize().background(white),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    val title = stringResource(R.string.security)
                    val description = stringResource(R.string.identiy_verification_before_enter)

                    //4 HERE WE SHOW THE AUTH OTOMATICA TO USER :
                    LaunchedEffect(Unit) {
                        authManager.showBiometricPrompt(title, description)
                    }
                    //1 HERE WE ACTCH THE RESULT AND REACT :
                    authResult?.let { RESULT ->
                        when (RESULT) {
                            is LocalAuthManager.AuthResult.AuthenticationError -> {
                                ShowAuthErrorMassage(
                                    theText = stringResource(R.string.identiy_verification_before_enter),
                                    OnTheClick = {
                                        authManager.showBiometricPrompt(title, description)
                                    }
                                )
                            }

                            LocalAuthManager.AuthResult.AuthenticationFailed -> {
                                ShowAuthErrorMassage(
                                    theText = stringResource(R.string.identiy_verification_before_enter),
                                    OnTheClick = {
                                        authManager.showBiometricPrompt(title, description)
                                    }
                                )
                            }

                            LocalAuthManager.AuthResult.AuthenticationNotSet -> {
                                ShowAuthErrorMassage(
                                    theText = stringResource(R.string.identiy_verification_before_enter),
                                    OnTheClick = {
                                        authManager.showBiometricPrompt(title, description)
                                    }
                                )
                            }

                            LocalAuthManager.AuthResult.AuthenticationSuccess -> {

                                Change_Style()
                                backupViewModel.startDailyBackup()
                                MessagesOfToasts.SetMessageTests()
                                GetLanguage_ColorName.SetLanguage_ColorName()
                                val viewModel: WithdrawalTypeViewModel = viewModel()
                                val context = LocalContext.current
                                ShowScreens()

                            }

                            LocalAuthManager.AuthResult.FeatureUnavailable -> {
                                ShowAuthErrorMassage(
                                    theText = stringResource(R.string.identiy_verification_before_enter),
                                    OnTheClick = {
                                        authManager.showBiometricPrompt(title, description)
                                    }
                                )
                            }

                            LocalAuthManager.AuthResult.HardwareUnavailable -> {
                                ShowAuthErrorMassage(
                                    theText = stringResource(R.string.identiy_verification_before_enter),
                                    OnTheClick = {
                                        authManager.showBiometricPrompt(title, description)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ShowAuthErrorMassage(theText: String, OnTheClick: () -> Unit) {
    Text(
        text = theText,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Cursive
    )
    Spacer(Modifier.height(12.dp))
    Button(

        onClick = {
            OnTheClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = blueLight
        )
    ) {
        Text(
            text = stringResource(R.string.enter),
            color = black
        )
    }
}
