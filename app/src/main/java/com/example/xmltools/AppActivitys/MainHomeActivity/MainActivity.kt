package com.money.trackpay.AppActivitys.MainHomeActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.xmltools.AppActivitys.Nav_Size.ShowScreens
import com.example.xmltools.Model.All_Withdrawal_data.messages_ofToast.MessagesOfToasts
import com.example.xmltools.Model.All_Withdrawal_data.Language_Color_Lists.GetLanguage_ColorName
import com.example.xmltools.ViewModels.Automatic_Save_VM.BackupViewModel
import com.example.xmltools.ViewModels.WithdrawalType_VM.WithdrawalTypeViewModel
import com.example.xmltools.ui.theme.Change_Style
import com.example.xmltools.ui.theme.XMLToolsTheme

class MainActivity : ComponentActivity() {
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




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    XMLToolsTheme {

    }
}