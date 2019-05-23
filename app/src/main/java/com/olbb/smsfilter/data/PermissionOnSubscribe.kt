package com.olbb.smsfilter.data

import android.content.Context
import com.github.dfqin.grantor.PermissionListener
import com.github.dfqin.grantor.PermissionsUtil
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import java.lang.Exception

class PermissionOnSubscribe(private val context:Context, private val permissions:Array<String>): SingleOnSubscribe<Boolean> {

    @Throws(Exception::class)
    override fun subscribe(emitter: SingleEmitter<Boolean>) {

        var has = PermissionsUtil.hasPermission(context, *permissions)
        if (has) {
            emitter.onSuccess(true)
        } else {
            PermissionsUtil.requestPermission(context,object: PermissionListener{
                override fun permissionDenied(permission: Array<out String>) {
                    emitter.onError(Exception("User denied the permission."))
                }

                override fun permissionGranted(permission: Array<out String>) {
                    emitter.onSuccess(true)
                }
            }, *permissions)
        }

    }

}