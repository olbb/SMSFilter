package com.olbb.smsfilter.util

import android.Manifest

object Constants {

    val NEED_PERMISSION = arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)

    const val SMS_PATH = "content://sms"

    const val SMS_VOLUME_ID = "_id"
    const val SMS_VOLUME_ADDRESS = "address"
    const val SMS_VOLUME_BODY = "body"
    const val SMS_VOLUME_DATE = "date"
    const val SMS_VOLUME_PERSON = "person"
    const val SMS_VOLUME_TYPE = "type"

    val SMS_STR = arrayOf(SMS_VOLUME_ID, SMS_VOLUME_ADDRESS,SMS_VOLUME_BODY, SMS_VOLUME_DATE, SMS_VOLUME_PERSON, SMS_VOLUME_TYPE)

}
