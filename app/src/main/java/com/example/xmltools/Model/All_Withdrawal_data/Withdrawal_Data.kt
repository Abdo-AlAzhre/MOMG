package com.example.xmltools.Model.All_Withdrawal_data

import com.money.trackpay.R
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.util.Calendar

private var calendar = Calendar.getInstance()

open class LocaleDataBase : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()

    var amount: Double = 0.0
    var items: String = ""
    var type: String = ""

    var day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    var month: Int = calendar.get(Calendar.MONTH) + 1
    var year: Int = calendar.get(Calendar.YEAR)
    var date: String = "$day/$month/$year"

    var withdrawalIcon:Int = R.drawable.withdrawal
}

val listOfMonths = listOf(
    "1", "2", "3", "4", "5", "6",
    "7", "8", "9", "10", "11", "12"
)
val listOfYears = (2000..calendar.get(Calendar.YEAR)).toList()


