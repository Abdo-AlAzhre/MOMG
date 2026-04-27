package com.example.MoneyManager.AppADS

import android.content.Context
import com.startapp.sdk.adsbase.Ad
import com.startapp.sdk.adsbase.StartAppAd
import com.startapp.sdk.adsbase.adlisteners.AdEventListener


fun ImageADS(context: Context) {

    val adsSetting = StartAppAd(context)

    adsSetting.loadAd(
        StartAppAd.AdMode.AUTOMATIC,
        object : AdEventListener {
            override fun onReceiveAd(p0: Ad) {
                adsSetting.showAd()
            }

            override fun onFailedToReceiveAd(p0: Ad?) {}
        }
    )

}