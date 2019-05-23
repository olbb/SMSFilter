package com.olbb.smsfilter.util

import android.Manifest

object Constants {

    val NEED_PERMISSION = arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)

    const val SMS_PATH = "content://sms"

    val SMS_STRS = arrayOf("_id", "address", "body", "date", "person", "type")

}
