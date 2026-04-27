package com.example.xmltools.creating_realm_data.Realm_DepositAndWithdrawal

import com.example.xmltools.Model.All_Deposit_data.Deposit_data
import com.example.xmltools.Model.All_Withdrawal_data.Withdrawal_Data
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object Realm_DepositAndWithdrawal {
    val realm: Realm by lazy {
        val config =
            RealmConfiguration.Builder(schema = setOf(Deposit_data::class, Withdrawal_Data::class))
                .name("data.realm")
                .build()
        Realm.Companion.open(config)
    }
}