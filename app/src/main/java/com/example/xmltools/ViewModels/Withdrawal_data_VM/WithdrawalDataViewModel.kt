package com.example.xmltools.ViewModels.Locale_data_VM

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.xmltools.Model.All_Withdrawal_data.LocaleDataBase
import com.example.xmltools.Model.All_Withdrawal_data.messages_ofToast.MessagesOfToasts
import com.example.xmltools.creating_realm_data.Realm_Withdrawal_LocalData.SavingLocaleData
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

class LocaleDataViewModel(private val realm: Realm = SavingLocaleData.realmLocaleData) :
    ViewModel() {

    private val auth = Firebase.auth
    private val db = Firebase.firestore

    // to get All Data we need to use :
    val dataFlow = realm.query<LocaleDataBase>().asFlow().map { it.list }.stateIn(
        viewModelScope, SharingStarted.Companion.Eagerly, emptyList()
    )

    // to show Toast Message to user :
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    // to add new data to Locale and claude data :
    fun addNewData(
        locateDataBase: LocaleDataBase,
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

    // to edit data  we use :
    fun editData(
        locateDataBase: LocaleDataBase,
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
                    query<LocaleDataBase>("id == $0", locateDataBase.id).first().find()
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

    // to delete all data in this app :
    fun deleteAllData() {
        viewModelScope.launch {
            realm.write {
                val allData = query<LocaleDataBase>().find()
                delete(allData)
            }
        }
    }

    // to delete some data from all data :
    fun deleteData(
        locateDataBase: LocaleDataBase,
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

    // to save bacup in Downloads Automatically :
    fun backupRealmDatabase(
        context: Context,
        showProgress: () -> Unit,
        hiedProgress: () -> Unit,
    ) {
        viewModelScope.launch {
            showProgress()
            showToast(context = context , message = MessagesOfToasts.saving)
            delay(1500)
            val realm = SavingLocaleData.realmLocaleData
            val realmFile = File(realm.configuration.path)
            // المجلد اللي هتنسخ فيه
            val backupDir = File(context.getExternalFilesDir(null), "RealmBackup")
            if (!backupDir.exists()) backupDir.mkdirs()
            // اسم الملف الاحتياطي
            val backupFile = File(backupDir, "realm_backup.realm")
            realmFile.copyTo(backupFile, overwrite = true)
            hiedProgress()
            showToast(context = context , message = MessagesOfToasts.successfully_saving)
        }
    }

    // to restore data from Downloads :
    fun restoreRealmDatabase(
        context: Context,
        showProgress: () -> Unit,
        hiedProgress: () -> Unit,
    ) {
        showProgress()
        val realm = SavingLocaleData.realmLocaleData
        val realmFile = File(realm.configuration.path)
        val backupFile = File(context.getExternalFilesDir(null), "RealmBackup/realm_backup.realm")
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