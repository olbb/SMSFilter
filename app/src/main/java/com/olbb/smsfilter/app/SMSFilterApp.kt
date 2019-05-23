package com.olbb.smsfilter.app

import android.app.Application
import android.util.Log

class SMSFilterApp : Application() {

    private val tag = "SMSFilterApp"

    override fun onCreate() {
        super.onCreate()
        Log.i(tag, "onCreate")
    }
}
