package com.example.xmltools.ViewModels

import androidx.lifecycle.ViewModel
import com.example.xmltools.Model.All_Locale_data.LocaleDataBase
import com.example.xmltools.creating_realm_data.SavingLocaleData
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AnnualViewModel(private val realm: Realm = SavingLocaleData.realmLocaleData) : ViewModel() {

    //  تجيب كل بيانات سنة معينة
    fun getDataByYear(year: Int): Flow<List<LocaleDataBase>> {
        return realm.query<LocaleDataBase>(
            "year == $0", year
        ).asFlow().map { it.list }
    }

    //  تجمع كل المصاريف حسب الشهر (للسنة كلها)
    fun getMonthlyTotals(data: List<LocaleDataBase>): List<Pair<Int, Double>> {
        return data
            .groupBy { it.month } // نجمع حسب الشهر
            .map { (month, list) ->
                month to list.sumOf { it.amount }
            }
            .sortedBy { it.first } // نرتب الشهور تصاعديًا
    }

    //  الإجمالي السنوي
    fun getYearTotal(data: List<LocaleDataBase>): Double {
        return data.sumOf { it.amount }
    }

}