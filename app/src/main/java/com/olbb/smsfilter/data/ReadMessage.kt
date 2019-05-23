package com.olbb.smsfilter.data

import android.content.Context
import android.net.Uri
import com.olbb.smsfilter.util.Constants
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe

class ReadMessage(private val context: Context):SingleOnSubscribe<List<Message>> {

    override fun subscribe(emitter: SingleEmitter<List<Message>>) {
        val cursor = context.contentResolver.query(Uri.parse(Constants.SMS_PATH), Constants.SMS_STR, null, null, "date desc")
        val list = mutableListOf<Message>()
        while (cursor?.moveToNext() == true) {
//            val count = cursor.columnCount
//            for (index in 0 until count) {
//                val  name = cursor.getColumnName(index)
//                println(name + ":" +cursor.getString(index))
//            }
            list.add(Message(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                cursor.getLong(3), cursor.getString(4), cursor.getInt(5)))

        }
        cursor?.close()
        emitter.onSuccess(list)
    }

}