package com.example.MoneyManager.ViewModels.WithdrawalType_VM

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.MoneyManager.Model.All_TypeItems.StaticWithdrawalTypes_List.WithdrawalTypeItems
import com.example.MoneyManager.creating_realm_data.Realm_WithdrawalType.SaveWithdrawalType
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class WithdrawalTypeViewModel(private val realm: Realm = SaveWithdrawalType.realm) : ViewModel() {

    // we will use ths value to show our items in ui :
    val withdrawalTypesFlow = realm.query<WithdrawalTypeItems>().asFlow().map { it.list }

    // we will use this function to show message to user :
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    // we will use this function to manual add new data to realm :
    fun addWithdrawalType(context: Context, name: String) {
        viewModelScope.launch {
            if (name.isNotBlank() && name.isNotEmpty()) {
                try {
                    realm.write {
                        copyToRealm(WithdrawalTypeItems().apply { this.WithdrawalType = name })
                    }

                } catch (e: Exception) {
                    showToast(context, "error : $e")
                }
            }
        }
    }

    // we will use this function to edit data  in realm :
    fun updateType(context: Context, id: ObjectId, newName: String) {
        viewModelScope.launch {
            realm.write {
                val item = query<WithdrawalTypeItems>("id == $0", id).first().find()
                if (newName.isNotEmpty() && newName.isNotBlank()) {
                    try {
                        item?.WithdrawalType = newName
                    } catch (e: Exception) {

                        showToast(context, "error : $e")
                    }
                } else {
                    showToast(context, "")
                }
            }
        }
    }

    // we use this function to delete data from realm :
    fun deleteType(context: Context, id: ObjectId) {
        viewModelScope.launch {
            realm.write {
                val item = query<WithdrawalTypeItems>("id == $0", id).first().find()
                try {
                    if (item != null) {
                        delete(item)
                    }
                } catch (e: Exception) {
                    showToast(context, "error : $e")
                }
            }
        }
    }

}