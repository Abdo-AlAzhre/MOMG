package com.example.xmltools.ViewModels.Deposit_and_Withdrawal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xmltools.Model.All_Deposit_data.Deposit_data
import com.example.xmltools.Model.All_Withdrawal_data.Withdrawal_Data
import com.example.xmltools.ViewModels.DepositDataViewModel.DepositDataViewModel
import com.example.xmltools.ViewModels.Withdrawal_data_VM.WithdrawalDataViewModel
import com.example.xmltools.creating_realm_data.Realm_DepositAndWithdrawal.Realm_DepositAndWithdrawal
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlin.collections.sortedByDescending

//3 this sealed class to choose what data we used :
sealed class TransactionUiModel {
    data class Deposit(val data: Deposit_data) : TransactionUiModel()
    data class Withdrawal(val data: Withdrawal_Data) : TransactionUiModel()
}


class DepositAndWithdrawalViewModel : ViewModel() {
    //` here we use realm of all deposit and withdrawal data :
    private val realm = Realm_DepositAndWithdrawal.realm

    private val depositFlow = DepositDataViewModel().depositDataFlow
    private val withdrawalFlow = WithdrawalDataViewModel().withdrawalDataFlow

    //~ here we transactied data to TransactionUiModel :
    val ALL_DATA: StateFlow<List<TransactionUiModel>> =
        combine(flow = depositFlow, flow2 = withdrawalFlow) { deposits, withdrawals ->
            val depositItems = deposits.map { TransactionUiModel.Deposit(it) }
            val withdrawalItems = withdrawals.map { TransactionUiModel.Withdrawal(it) }


            (depositItems + withdrawalItems).sortedByDescending {
                when (it) {
                    is TransactionUiModel.Deposit -> it.data.currentTimeMillis
                    is TransactionUiModel.Withdrawal -> it.data.currentTimeMillis
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

}