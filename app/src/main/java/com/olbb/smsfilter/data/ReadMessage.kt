package com.olbb.smsfilter.data

import android.content.Context
import android.net.Uri
import com.olbb.smsfilter.util.Constants
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe

class ReadMessage(private val context: Context):SingleOnSubscribe<String> {

    override fun subscribe(emitter: SingleEmitter<String>) {
        val cursor = context.contentResolver.query(Uri.parse(Constants.SMS_PATH), Constants.SMS_STRS, null, null, "date desc")
        while (cursor.moveToNext()) {

        }
        cursor.close()
    }

}