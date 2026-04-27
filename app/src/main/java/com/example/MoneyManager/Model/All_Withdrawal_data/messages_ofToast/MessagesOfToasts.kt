package com.example.MoneyManager.Model.All_Withdrawal_data.messages_ofToast

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.money.trackpay.R

object MessagesOfToasts {
    var make_sure_of_entering_all_data = ""
    var make_sure_of_enter_email = ""
    var category_added_successfully = ""

    var amount_must_be_more_then_0 =""
    var add_category_name_to_continue =""
    var saving =""

    var successfully_saving =""
    var deleting =""
    var deleting_successfully =""

    var data_recovery =""
    var data_has_been__recovered =""
    var no_data =""

    var you_need_to_mack_sure_of_your_data =""
    var entering =""
    var welcome_back =""

    var try_again_later =""
    var creating_new_account =""
    var data_is_being_saved_before_exiting =""

    var see_you_again =""
    var try_again =""
    var sending_the_code =""

    var check_your_mail =""
    var the_code_was_not_sent_try_again =""
    var make_sure_the_amount_is_in_numbers_only =""

    var you_forgot_to_add_category =""
    var dont_forget_date =""

    @Composable
    fun SetMessageTests() {
        make_sure_of_entering_all_data = stringResource(R.string.make_sure_of_entering_all_data)
        make_sure_of_enter_email = stringResource(R.string.make_sure_of_enter_email)
        category_added_successfully = stringResource(R.string.category_added_successfully)

        amount_must_be_more_then_0 = stringResource(R.string.amount_must_be_more_then_0)
        add_category_name_to_continue = stringResource(R.string.add_category_name_to_continue)
        saving = stringResource(R.string.saving)

        successfully_saving = stringResource(R.string.successfully_saving)
        deleting = stringResource(R.string.deleting)
        deleting_successfully = stringResource(R.string.deleting_successfully)

        data_recovery = stringResource(R.string.data_recovery)
        data_has_been__recovered = stringResource(R.string.data_has_been__recovered)
        no_data = stringResource(R.string.no_data)

        you_need_to_mack_sure_of_your_data = stringResource(R.string.you_need_to_mack_sure_of_your_data)
        entering = stringResource(R.string.entering)
        welcome_back = stringResource(R.string.welcome_back)

        try_again_later = stringResource(R.string.try_again_later)
        creating_new_account = stringResource(R.string.creating_new_account)
        data_is_being_saved_before_exiting = stringResource(R.string.data_is_being_saved_before_exiting)

        see_you_again = stringResource(R.string.see_you_again)
        try_again = stringResource(R.string.try_again)
        sending_the_code = stringResource(R.string.sending_the_code)

        check_your_mail = stringResource(R.string.check_your_mail)
        the_code_was_not_sent_try_again = stringResource(R.string.the_code_was_not_sent_try_again)
        make_sure_the_amount_is_in_numbers_only = stringResource(R.string.make_sure_the_amount_is_in_numbers_only)

        you_forgot_to_add_category = stringResource(R.string.you_forgot_to_add_category)
        dont_forget_date = stringResource(R.string.dont_forget_date)

    }
}
