package com.example.MoneyManager.Model.All_TypeItems.StaticDepositType

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class DepositTypes : RealmObject {
    @PrimaryKey
    var id : ObjectId = BsonObjectId.Companion()
    var depositType : String = ""
}