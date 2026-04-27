package com.example.xmltools.creating_realm_data.Realm_Deposit_LocalData

import com.example.xmltools.Model.All_Deposit_data.Deposit_data
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object SaveDepositData {
    val realmDepositData: Realm by lazy {
        val config = RealmConfiguration.Builder(schema = setOf(Deposit_data::class))
            .name("deposit data.realm")
            .build()
        Realm.Companion.open(config)
    }
}