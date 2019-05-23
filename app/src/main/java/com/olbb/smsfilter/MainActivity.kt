package com.olbb.smsfilter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.olbb.smsfilter.util.Constants
import com.olbb.smsfilter.data.PermissionOnSubscribe
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun readMsg(view:View) {
        readMessage()
    }

    private fun checkPermission(): PermissionOnSubscribe {
        return PermissionOnSubscribe(this, Constants.NEED_PERMISSION)
    }

    private fun readMessage() {
        Single.create(checkPermission()).subscribe(read())
    }

    private fun read(): SingleObserver<Boolean> {
        return object : SingleObserver<Boolean> {
            override fun onSuccess(t: Boolean) {
                println("onSuccess:$t")
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
                println(e)
            }
        }
    }
}
