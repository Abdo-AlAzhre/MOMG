package com.example.MoneyManager.Model.All_TypeItems.StaticWithdrawalTypes_List

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class WithdrawalTypeItems: RealmObject {
    @PrimaryKey
    var id : ObjectId = BsonObjectId.Companion()
    var WithdrawalType:String = ""
}