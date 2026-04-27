package com.example.MoneyManager.ViewModels.DepositType_VM

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.MoneyManager.Model.All_TypeItems.StaticDepositType.DepositTypes
import com.example.MoneyManager.creating_realm_data.Realm_DepositType.Save_DepositType
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class DepositTypeViewModel : ViewModel() {

    //4 here we created variable to  get our realm database to save and get items from it.
    private val realm: Realm = Save_DepositType.realm;

    //4 To GetData from RealmDataBase and set it to UI :
    val depositTypesFlow = realm.query<DepositTypes>().asFlow().map { it.list }

    //4 this fun to show message to user :
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    //4 This fun to add new Deposit Types :
    fun addDepositType(context: Context, name: String) {
        viewModelScope.launch {
            try {
                realm.write {
                    copyToRealm(
                        DepositTypes().apply {
                            this.depositType = name
                        })
                }
            } catch (e: Exception) {
                showToast(context, message = " the Error is : $e")
            }
        }
    }

    //4 This fun to Edit Deposit Types :
    fun editDepositType(context: Context, name: String, id: ObjectId) {
        viewModelScope.launch {
            realm.write {
                val item = query<DepositTypes>("id == $0", id).first().find()
                try {

                    item?.depositType = name

                } catch (e: Exception) {
                    showToast(context, message = " the Error is : $e")
                }
            }
        }
    }

    //4 This fun to Delete the Deposit Types :
    fun deleteDepositType(context: Context, id: ObjectId) {
        viewModelScope.launch {
            realm.write {
                val item = query<DepositTypes>("id == $0", id)
                try {
                    delete(item!!)
                } catch (e: Exception) {
                    showToast(context, message = " the Error is : $e")
                }

            }
        }
    }

}