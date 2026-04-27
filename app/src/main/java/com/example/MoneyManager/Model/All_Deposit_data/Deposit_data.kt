package com.example.MoneyManager.Model.All_Deposit_data

import com.money.trackpay.R
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId
import java.util.Calendar


private var calendar = Calendar.getInstance()

class Deposit_data : RealmObject {
    @PrimaryKey
    var id: ObjectId = BsonObjectId.Companion()

    var depositAmount: Double = 0.0
    var depositType: String = ""
    var depositDescriptor: String = ""

    var day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    var month: Int = calendar.get(Calendar.MONTH) + 1
    var year: Int = calendar.get(Calendar.YEAR)
    var currentTimeMillis = System.currentTimeMillis() //~ this  variable is to arrange data by time that user saved this data in :

    var depositIcon: Int = R.drawable.deposit

    var date: String = "$day/$month/$year"
}