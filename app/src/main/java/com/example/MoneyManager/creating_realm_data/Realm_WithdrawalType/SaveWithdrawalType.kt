package com.example.MoneyManager.creating_realm_data.Realm_WithdrawalType

import com.example.MoneyManager.Model.All_TypeItems.StaticWithdrawalTypes_List.WithdrawalTypeItems
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object SaveWithdrawalType {
    val realm: Realm by lazy {
        val config = RealmConfiguration.Companion.create(schema = setOf(WithdrawalTypeItems::class))
        Realm.Companion.open(config)
    }
}