package com.money.trackpay.AppActivitys.MainHomeActivity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.MoneyManager.AppActivitys.Nav_Size.ShowScreens
import com.example.MoneyManager.LocalAuth.LocalAuthManager
import com.example.MoneyManager.Model.All_Withdrawal_data.messages_ofToast.MessagesOfToasts
import com.example.MoneyManager.Model.All_Withdrawal_data.Language_Color_Lists.GetLanguage_ColorName
import com.example.MoneyManager.ViewModels.Automatic_Save_VM.BackupViewModel
import com.example.MoneyManager.ViewModels.WithdrawalType_VM.WithdrawalTypeViewModel
import com.example.MoneyManager.ui.theme.Change_Style
import com.example.MoneyManager.ui.theme.XMLToolsTheme

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
                Change_Style()
                backupViewModel.startDailyBackup()
                MessagesOfToasts.SetMessageTests()
                GetLanguage_ColorName.SetLanguage_ColorName()

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    ShowScreens()
                   val viewModel: WithdrawalTypeViewModel = viewModel()
                    val context = LocalContext.current

                }
            }
        }
    }
}