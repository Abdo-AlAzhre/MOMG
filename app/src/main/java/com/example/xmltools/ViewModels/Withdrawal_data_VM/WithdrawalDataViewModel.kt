package com.example.xmltools.ViewModels.Withdrawal_data_VM

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.xmltools.Model.All_Withdrawal_data.Withdrawal_Data
import com.example.xmltools.Model.All_Withdrawal_data.messages_ofToast.MessagesOfToasts
import com.example.xmltools.creating_realm_data.Realm_Withdrawal_LocalData.SavingWithdrawalData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File

class WithdrawalDataViewModel(private val realm: Realm = SavingWithdrawalData.realmWithdrawal) :
    ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore

    //` to get All Data we need to use :
    val withdrawalDataFlow = realm.query<Withdrawal_Data>().asFlow().map { it.list }.stateIn(
        viewModelScope, SharingStarted.Companion.Eagerly, emptyList()
    )

    //` to show Toast Message to user :
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    //` to add new data to Locale and claude data :
    fun addNewData(
        locateDataBase: Withdrawal_Data,
        context: Context,
        navController: NavHostController,
        navTo: String,
        onAddFun: () -> Unit
    ) {
        viewModelScope.launch {
            showToast(context, MessagesOfToasts.saving)
            realm.write {
                copyToRealm(locateDataBase)
            }
            onAddFun()
            delay(300)
            navController.navigate(navTo) {
                popUpTo(navTo)
            }
            showToast(context, MessagesOfToasts.successfully_saving)
        }
    }

    //` to edit data  we use :
    fun editData(
        locateDataBase: Withdrawal_Data,
        context: Context,
        navController: NavHostController,
        navTo: String,
        onEditFun: () -> Unit
    ) {
        viewModelScope.launch {
            showToast(context, MessagesOfToasts.saving)
            delay(300)
            realm.write {
                val existingItem =
                    query<Withdrawal_Data>("id == $0", locateDataBase.id).first().find()
                if (existingItem != null) {
                    existingItem.amount = locateDataBase.amount
                    existingItem.items = locateDataBase.items
                    existingItem.day = locateDataBase.day
                    existingItem.month = locateDataBase.month
                    existingItem.year = locateDataBase.year
                    existingItem.type = locateDataBase.type
                    existingItem.date = locateDataBase.date

                } else {
                    showToast(context, MessagesOfToasts.you_need_to_mack_sure_of_your_data)
                }
                onEditFun()
            }
            navController.navigate(navTo) {
                popUpTo(navTo)
            }
            showToast(context, MessagesOfToasts.successfully_saving)
        }
    }

    //` to delete all data in this app :
    fun deleteAllData() {
        viewModelScope.launch {
            realm.write {
                val allData = query<Withdrawal_Data>().find()
                delete(allData)
            }
        }
    }

    //` to delete some data from all data :
    fun deleteData(
        locateDataBase: Withdrawal_Data,
        context: Context,
        navTo: String,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            showToast(context, MessagesOfToasts.deleting)
            delay(300)
            realm.write {
                findLatest(locateDataBase)?.let { delete(it) }
            }
            navController.navigate(navTo) {
                popUpTo(navTo)
            }
            showToast(context, MessagesOfToasts.deleting_successfully)
        }
    }

    //` to save bacup in Downloads  :
    fun backupWithdrawalData(
        context: Context,
        showProgress: () -> Unit,
        hiedProgress: () -> Unit,
    ) {
        viewModelScope.launch {
            showProgress()
            showToast(context = context , message = MessagesOfToasts.saving)
            delay(1500)
            val realmFile = File(realm.configuration.path)
            // المجلد اللي هتنسخ فيه
            val backupDir = File(context.getExternalFilesDir(null), "withdrawalBackup")
            if (!backupDir.exists()) backupDir.mkdirs()
            // اسم الملف الاحتياطي
            val backupFile = File(backupDir, "withdrawalBackup.realm")
            realmFile.copyTo(backupFile, overwrite = true)
            hiedProgress()
            showToast(context = context , message = MessagesOfToasts.successfully_saving)
        }
    }

    //` to restore data from Downloads :
    fun restoreWithdrawalData(
        context: Context,
        showProgress: () -> Unit,
        hiedProgress: () -> Unit,
    ) {
        showProgress()
        val realm = SavingWithdrawalData.realmWithdrawal
        val realmFile = File(realm.configuration.path)
        val backupFile = File(context.getExternalFilesDir(null), "withdrawalBackup/withdrawalBackup.realm")
        viewModelScope.launch {
            showToast(context = context, MessagesOfToasts.data_recovery)
            delay(2000)
            if (backupFile.exists()) {
                hiedProgress()
                backupFile.copyTo(realmFile, overwrite = true)
                showToast(context, MessagesOfToasts.data_has_been__recovered)

            } else {
                hiedProgress()
                showToast(context, MessagesOfToasts.no_data)
            }
        }
    }
}