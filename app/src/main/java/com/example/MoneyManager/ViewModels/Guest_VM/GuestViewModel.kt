package com.example.MoneyManager.ViewModels.Guest_VM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.MoneyManager.Model.All_Gust_Data.EnterUsingGuestData
import com.example.MoneyManager.creating_realm_data.Realm_Guest.SaveGuestData
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GuestViewModel(private val realm: Realm = SaveGuestData.realm) : ViewModel() {

    // We use this function to add and Change Gust State :
    fun saveGuestState(isGuest: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            realm.write {
                // Here we are deleting the old data from realm data :
                val oldData = this.query<EnterUsingGuestData>().find()
                delete(oldData)
                // Here we are adding the new data to realm data :
                copyToRealm(EnterUsingGuestData().apply {
                    isItTrue = isGuest
                })
            }
        }
    }


    // the function to get and show the amount of Guest:
    fun getGuestState(): Boolean {
        val data = realm.query<EnterUsingGuestData>().first().find()
        return data?.isItTrue ?: false
    }

}