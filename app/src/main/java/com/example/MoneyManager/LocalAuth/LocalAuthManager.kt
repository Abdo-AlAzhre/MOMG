package com.example.MoneyManager.LocalAuth

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class LocalAuthManager(
    private val activity: AppCompatActivity
) {

    private val resultChannel = Channel<AuthResult>()

    val promptResult = resultChannel.receiveAsFlow()//2 we will use this to get and use the result :

    //1 THIS FUNCTION WE WILL USE TO GET THE AUTH FROM USER :
    fun showBiometricPrompt(
        title: String,
        description: String,
    ) {

        val manager = BiometricManager.from(activity)

        //` here we chooses which security auth we will use in user`s phone :
        val authenticator = if (Build.VERSION.SDK_INT >= 30) {
            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
        } else {
            BIOMETRIC_STRONG
        }

        //` HERE WE BUILD USER AUTH SCREEN :
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setDescription(description)
            .setAllowedAuthenticators(authenticator)
            .setConfirmationRequired(false)
        if (Build.VERSION.SDK_INT < 30) {
            promptInfo.setNegativeButtonText("Cansel")
        }

        //` HERE WE USING THE SEALED INTERFACE TO  CHECK THE SENSORS :
        when (manager.canAuthenticate(authenticator)) {

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                resultChannel.trySend(AuthResult.HardwareUnavailable)
                return
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                resultChannel.trySend(AuthResult.FeatureUnavailable)
                return
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                resultChannel.trySend(AuthResult.AuthenticationNotSet)
                return
            }

            else -> Unit
        }

        //` HERE WE CHECK THE INPUTS FROM USER :
        val prompt = BiometricPrompt(
            activity,
            object : BiometricPrompt.AuthenticationCallback() {
                //3 what will hapen if user succeded with auth :
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    resultChannel.trySend(AuthResult.AuthenticationSuccess)
                }

                //3 what will hapen if user have an error with auth :
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    resultChannel.trySend(AuthResult.AuthenticationError(errString.toString()))
                }

                //3 what will hapen if user failed with auth :
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    resultChannel.trySend(AuthResult.AuthenticationFailed)
                }
            }

        )


    }


    //1 THIS INTERFACE IS TO CREATE THE RESULT OF AUTH USE :
    sealed interface AuthResult {

        data object HardwareUnavailable : AuthResult
        data object FeatureUnavailable : AuthResult
        data object AuthenticationFailed : AuthResult
        data object AuthenticationSuccess : AuthResult
        data object AuthenticationNotSet : AuthResult
        data class AuthenticationError(val error: String) : AuthResult

    }


}