package com.example.MoneyManager.ViewModels.Annual_Monthly_VM

import androidx.lifecycle.ViewModel
import com.example.MoneyManager.Model.All_Deposit_data.Deposit_data
import com.example.MoneyManager.Model.All_Withdrawal_data.Withdrawal_Data
import com.example.MoneyManager.creating_realm_data.Realm_Deposit_LocalData.SaveDepositData
import com.example.MoneyManager.creating_realm_data.Realm_Withdrawal_LocalData.SavingWithdrawalData
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AnnualViewModel() : ViewModel() {

    private val withdrawalRealm: Realm = SavingWithdrawalData.realmWithdrawal
    private val depositRealm: Realm = SaveDepositData.realmDepositData

    /*~ THIS IS WITHDRAWAL`S SECTION */
    //4 here we get the data of all withdrawal data Year :
    fun getWithdrawalDataByYear(year: Int): Flow<List<Withdrawal_Data>> {
        return withdrawalRealm.query<Withdrawal_Data>(
            "year == $0", year
        ).asFlow().map { it.list }
    }
    //4 here we get the all withdrawal data from month :
    fun getWithdrawalMonthlyTotals(data: List<Withdrawal_Data>): List<Pair<Int, Double>> {
        return data
            .groupBy { it.month }
            .map { (month, list) ->
                month to list.sumOf { it.amount }
            }
            .sortedBy { it.first }
    }
    //4 here we sum the all amount of withdrawal in all months :
    fun getWithdrawalYearTotal(data: List<Withdrawal_Data>): Double {
        return data.sumOf { it.amount }
    }

    /*~ THIS IS DEPOSIT`S SECTION */

    //4 here we get the data of all deposit data Year :
    fun getDepositDataByYear(year: Int): Flow<List<Deposit_data>> {
        return depositRealm.query<Deposit_data>("year==$0", year).asFlow().map { it.list }
    }
    //4 here we get the all deposit data from month :
    fun getDepositMonthlyTotals(data: List<Deposit_data>): List<Pair<Int, Double>> {
        return data
            .groupBy { it.month }
            .map { (month, list) ->
                month to list.sumOf { it.depositAmount }
            }
            .sortedBy { it.first }
    }

    //4 here we sum the all amount of deposit in all months :
    fun getDepositYearTotal(data: List<Deposit_data>): Double {
        return data.sumOf { it.depositAmount }
    }
}