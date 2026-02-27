package com.example.xmltools.ViewModels

import androidx.lifecycle.ViewModel
import com.example.xmltools.Model.All_Locale_data.LocaleDataBase
import com.example.xmltools.creating_realm_data.SavingLocaleData
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MonthlyAnnualViewModel(private val realm: Realm = SavingLocaleData.realmLocaleData) :
    ViewModel() {

    //    To show only monthly data
    fun getDataByMonth(month: Int, year: Int): Flow<List<LocaleDataBase>> {
        return realm.query<LocaleDataBase>(
            "month == $0 AND year == $1",
            month, year
        ).asFlow().map { it.list }
    }

//    To show total
    fun getTotalAmount(data: List<LocaleDataBase>): Double {
        return data.sumOf { it.amount }
    }
}