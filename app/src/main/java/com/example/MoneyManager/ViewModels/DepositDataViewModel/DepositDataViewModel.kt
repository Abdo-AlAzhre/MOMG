package com.example.MoneyManager.ViewModels.DepositDataViewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.MoneyManager.Model.All_Deposit_data.Deposit_data
import com.example.MoneyManager.creating_realm_data.Realm_Deposit_LocalData.SaveDepositData
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import java.io.File

class DepositDataViewModel : ViewModel() {

    //` this is the variable of object that we created to save data on it :
    private val realm: Realm = SaveDepositData.realmDepositData

    //` this variable is to get data from our database( Deposit_data ) and use it as a list :
    val depositDataFlow = realm.query<Deposit_data>().asFlow().map { it.list }.stateIn(
        viewModelScope, SharingStarted.Companion.Eagerly, emptyList()
    )

    //` this fun to show message to user :
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    //` this fun to add new data to our database :
    fun addDepositData(
        context: Context,
        amount: Double,
        description: String,
        type: String,
        day: Int,
        month: Int,
        year: Int,
    ) {
        viewModelScope.launch {
            realm.write {
                try {
                    copyToRealm(
                        Deposit_data().apply {
                            this.depositDescriptor = description
                            this.depositType = type
                            this.depositAmount = amount
                            this.day = day
                            this.month = month
                            this.year = year
                        }
                    )
                } catch (e: Exception) {
                    showToast(context, " tha error is : $e")
                }
            }
        }
    }

    //` this fun to get and edit the old data in our database :
    fun editDepositData(
        context: Context,
        id: ObjectId,
        amount: Double,
        description: String,
        type: String,
        day: Int,
        month: Int,
        year: Int,
    ) {
        viewModelScope.launch {
            realm.write {
                try {
                    val item = query<Deposit_data>("id == $0", id).first().find()
                    item?.apply {
                        this.depositDescriptor = description
                        this.depositType = type
                        this.depositAmount = amount
                        this.day = day
                        this.month = month
                        this.year = year
                    }

                } catch (e: Exception) {
                    showToast(context, " tha error is : $e")
                }
            }

        }
    }

    //` this fun to delete any data from our databse :
    fun deleteDepositData(
        context: Context,
        id: ObjectId
    ) {
        viewModelScope.launch {
            realm.write {
                try {
                    val item = query<Deposit_data>("id == $0", id).first().find()
                    delete(item!!)
                } catch (e: Exception) {
                    showToast(context, " tha error is : $e")
                }
            }
        }
    }

    //` this fun to save bacup  in Downloads :
    fun bacupDepositData(
        context: Context
    ) {
        viewModelScope.launch {
            val realmFile = File(realm.configuration.path)
            val backupDir = File(context.getExternalFilesDir(null), "depositBacup")
            if (!backupDir.exists()) {
                backupDir.mkdirs()
            }
            val backupFile = File(backupDir, "deposit_Bacup.realm")
            realmFile.copyTo(backupFile, true)
        }
    }

    //` this fun to restore data from Downlods :
    fun restoreDepositData(
        context: Context
    ) {
        viewModelScope.launch {
            val realmFile = File(realm.configuration.path)
            val backupFile =
                File(context.getExternalFilesDir(null), "depositBacup/deposit_Bacup.realm")
            if (backupFile.exists()) {
                backupFile.copyTo(realmFile, true)
            }
        }
    }
}