package com.example.MoneyManager.creating_realm_data.Realm_DepositType

import com.example.MoneyManager.Model.All_TypeItems.StaticDepositType.DepositTypes
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object Save_DepositType {
    val realm: Realm by lazy {
        val config = RealmConfiguration.Builder(schema = setOf(DepositTypes::class))
            .name("Deposit types.realm")
            .build()
        Realm.Companion.open(config)
    }
}