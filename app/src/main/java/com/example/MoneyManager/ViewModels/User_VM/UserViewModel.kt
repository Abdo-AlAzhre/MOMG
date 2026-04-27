package com.example.MoneyManager.ViewModels.User_VM

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.MoneyManager.Model.All_Withdrawal_data.messages_ofToast.MessagesOfToasts
import com.example.MoneyManager.Model.All_UserData.UserInformation
import com.example.MoneyManager.creating_realm_data.Realm_User_Data.SavingUserData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(private val realm: Realm = SavingUserData.userRealm) : ViewModel() {

    val auth: FirebaseAuth = Firebase.auth

    // get All user information :
    val userFlow = realm.query<UserInformation>()
        .asFlow()
        .map { results -> results.list.firstOrNull() }.stateIn(
            viewModelScope,
            SharingStarted.Companion.Eagerly,
            null
        )

    // Add New User :
    fun addNewUser(user: String) {
        val existingUser = realm.query<UserInformation>().first().find()
        viewModelScope.launch {
            realm.write {
                copyToRealm(UserInformation().apply {
                    userName = user
                })
            }
        }
    }

    // to Delete User Information :
    fun deleteUser() {
        viewModelScope.launch {
            realm.write {
                val user = query<UserInformation>().first().find()
                user?.let { delete(it) }
            }
        }
    }

    // to show Toast message to user :
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    // we using this function to make user login using firebase method...:
    fun userLogin(
        name: String,
        password: String,
        context: Context,
        navController: NavHostController,
        showProgress: () -> Unit,
        hideProgress: () -> Unit,
    ) {
        showProgress()
        auth.signInWithEmailAndPassword(name, password).addOnCompleteListener { task ->
            showToast(context, MessagesOfToasts.entering)
            viewModelScope.launch {
                delay(3000)
                if (task.isSuccessful) {
                    navController.navigate("Compact_home") {
                        popUpTo(0) { inclusive = true } // back stack
                        launchSingleTop = true
                    }
                    showToast(context, MessagesOfToasts.welcome_back)
                    delay(2000)
                    hideProgress()
                } else {
                    showToast(context, MessagesOfToasts.try_again_later)
                    hideProgress()
                }
            }
        }
    }

    // we using this function to make user ( sigIn && Create new Account )  using firebase method...:
    fun userSignIn(
        email: String,
        password: String,
        context: Context,
        navController: NavHostController,
        showProgress: () -> Unit,
        hideProgress: () -> Unit,
        function: () -> Unit
    ) {
        showProgress()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                showToast(context, MessagesOfToasts.creating_new_account)
                viewModelScope.launch {
                    delay(2000)
                    if (it.isSuccessful) {
                        function()
                        hideProgress()
                        navController.navigate("Compact_home") {
                            popUpTo(0) { inclusive = true } // back stack
                            launchSingleTop = true
                        }
                        val user = auth.currentUser
                        showToast(context, MessagesOfToasts.welcome_back)
                    } else {
                        hideProgress()
                        showToast(context, MessagesOfToasts.try_again)
                    }
                }
            }
    }

    // we use this function to make user logout from the app:
    fun userLogOut(
        context: Context,
        navController: NavHostController,
        showProgress: () -> Unit,
        hideProgress: () -> Unit,
        onLogout: () -> Unit
    ) {
        showProgress()
        if (auth.currentUser != null) {
            viewModelScope.launch {
                showToast(context, MessagesOfToasts.data_is_being_saved_before_exiting)
                onLogout()
                delay(5000)
                auth.signOut()
                hideProgress()
                showToast(context, MessagesOfToasts.see_you_again)
                navController.navigate("Compact_first") {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        } else {
            showToast(context, MessagesOfToasts.try_again_later)
            hideProgress()
        }

    }

    // we using this function to send new password to user`s email :
    fun sendNewPasswordToUserEmail(
        email: String,
        context: Context,
        navController: NavHostController,
        showProgress: () -> Unit,
        hideProgress: () -> Unit,
    ) {
        showProgress()
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                showToast(context, MessagesOfToasts.sending_the_code)
                viewModelScope.launch {
                    delay(1000)
                }
                if (it.isSuccessful) {
                    showToast(context, MessagesOfToasts.check_your_mail)
                    hideProgress()
                } else {
                    showToast(context, MessagesOfToasts.the_code_was_not_sent_try_again)
                    hideProgress()
                }
            }
    }

}