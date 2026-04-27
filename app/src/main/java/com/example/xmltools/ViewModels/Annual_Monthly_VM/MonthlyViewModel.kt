package com.example.xmltools.ViewModels.Annual_Monthly_VM

import androidx.lifecycle.ViewModel
import com.example.xmltools.Model.All_Deposit_data.Deposit_data
import com.example.xmltools.Model.All_Withdrawal_data.Withdrawal_Data
import com.example.xmltools.creating_realm_data.Realm_Deposit_LocalData.SaveDepositData
import com.example.xmltools.creating_realm_data.Realm_Withdrawal_LocalData.SavingWithdrawalData
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MonthlyViewModel() :
    ViewModel() {
    //~ here we import realm of withdrawal to get data from it :
    private val withdrawalRealm: Realm = SavingWithdrawalData.realmWithdrawal

    //~ here we import realm of deposit to get data from it :
    private val depositRealm: Realm = SaveDepositData.realmDepositData

    //`  this fun to get date by month and year of withdrawal :
    fun getWithdrawalMonthData(month: Int, year: Int): Flow<List<Withdrawal_Data>> {
        return withdrawalRealm.query<Withdrawal_Data>(
            "month == $0 AND year == $1",
            month, year
        ).asFlow().map { it.list }
    }

    //` this fun to get data by month and year of deposit :
    fun getDepositMonthData(month: Int, year: Int): Flow<List<Deposit_data>> {
        return depositRealm.query<Deposit_data>("month==$0 AND year==$1", month, year)
            .asFlow().map { it.list }
    }

    /* 4
           here we do som method to get all withdrawal && deposit :
    */

    //2 here we get sum of withdrawal amount in this month :
    fun getTotalWithdrawalMonthAmount(data: List<Withdrawal_Data>): Double {
        return data.sumOf { it.amount }
    }

    //2 here we get sum of deposit amount in this month :
    fun getTotalDepositMonthAmount(data: List<Deposit_data>): Double {
        return data.sumOf { it.depositAmount }

    }

}
