package com.olbb.smsfilter

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.olbb.smsfilter.data.Message
import com.olbb.smsfilter.data.PermissionOnSubscribe
import com.olbb.smsfilter.data.ReadMessage
import com.olbb.smsfilter.util.Constants
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : Activity() {

    private lateinit var list : RecyclerView
    private lateinit var adapter: SimpleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list = findViewById(android.R.id.list)
        list!!.layoutManager = (LinearLayoutManager(this, RecyclerView.VERTICAL, false))
        adapter = SimpleAdapter()
        list.adapter = adapter
    }

    fun readMsg(view:View) {
        readMessage()
    }

    private fun checkPermission(): PermissionOnSubscribe {
        return PermissionOnSubscribe(this, Constants.NEED_PERMISSION)
    }

    private fun readMessage() {
        Single.create(checkPermission()).subscribeOn(Schedulers.io()).flatMap { t -> Single.create(ReadMessage(this))}.observeOn(AndroidSchedulers.mainThread()).subscribe(read())
    }

    private fun read(): SingleObserver<List<Message>> {
        return object : SingleObserver<List<Message>> {
            override fun onSuccess(t: List<Message>) {
                println("onSuccess:$t")
                adapter.list = t
                adapter.notifyDataSetChanged()
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    private  class SimpleHolder(view:View) : RecyclerView.ViewHolder(view) {
        var sender:TextView = view.findViewById(R.id.list_item_sender_tv)
        var body:TextView = view.findViewById(R.id.list_item_message_body_tv)
    }

    private class SimpleAdapter() : RecyclerView.Adapter<SimpleHolder>() {

        var list:List<Message>? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleHolder {
            return SimpleHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_activity_list_item, parent, false))
        }

        override fun getItemCount(): Int {
            return list?.size?: 1
        }

        override fun onBindViewHolder(holder: SimpleHolder, position: Int) {
            var msg = getData(position)
            holder.sender.text = msg?.address
            holder.body.text = msg?.body
        }

        fun getData(position: Int):Message?{
            return list?.get(position)
        }

    }
}
