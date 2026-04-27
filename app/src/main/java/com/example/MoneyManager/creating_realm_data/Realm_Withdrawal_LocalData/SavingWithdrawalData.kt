package com.example.MoneyManager.creating_realm_data.Realm_Withdrawal_LocalData

import com.example.MoneyManager.Model.All_Withdrawal_data.Withdrawal_Data
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object SavingWithdrawalData {
    val realmWithdrawal : Realm by lazy {
        val config = RealmConfiguration.Companion.create(schema = setOf(Withdrawal_Data::class))
        Realm.Companion.open(config)
    }
}