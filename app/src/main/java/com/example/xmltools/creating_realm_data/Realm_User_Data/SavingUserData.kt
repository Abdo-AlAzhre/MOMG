package com.example.xmltools.creating_realm_data.Realm_User_Data

import com.example.xmltools.Model.All_UserData.UserInformation
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object SavingUserData {
    val userRealm : Realm by lazy {
        val config = RealmConfiguration.Companion.create(schema = setOf(UserInformation::class))
        Realm.Companion.open(config)
    }
}