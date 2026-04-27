package com.example.xmltools.creating_realm_data.Realm_Guest

import com.example.xmltools.Model.All_Gust_Data.EnterUsingGuestData
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object SaveGuestData {
    val realm: Realm by lazy {
        val config = RealmConfiguration.Builder(schema = setOf(EnterUsingGuestData::class))
            .name("guest_data.realm")
            .build()
        Realm.Companion.open(config)
    }
}