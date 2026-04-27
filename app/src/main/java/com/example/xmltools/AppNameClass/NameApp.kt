package com.example.xmltools.AppNameClass

import android.app.Application
import com.startapp.sdk.adsbase.StartAppSDK

class NameApp : Application() {
    override fun onCreate() {
        super.onCreate()
        StartAppSDK.init(this , "201672356")
    }
}