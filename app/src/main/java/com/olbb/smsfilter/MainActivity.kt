package com.olbb.smsfilter

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_activity_list_item.*

class MainActivity : Activity() {

    private lateinit var adapter: SimpleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.layoutManager = (LinearLayoutManager(this, RecyclerView.VERTICAL, false))
        adapter = SimpleAdapter()
        recycler.adapter = adapter
    }

    fun readMsg(view:View) {
        readMessage()
    }

    private fun checkPermission(): PermissionOnSubscribe {
        return PermissionOnSubscribe(this, Constants.NEED_PERMISSION)
    }

    private fun readMessage() {
        Single.create(checkPermission()).subscribeOn(Schedulers.io()).flatMap { Single.create(ReadMessage(this))}.observeOn(AndroidSchedulers.mainThread()).subscribe(read())
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

    private  class SimpleHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView!!), LayoutContainer

    private class SimpleAdapter: RecyclerView.Adapter<SimpleHolder>() {

        var list:List<Message>? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleHolder {
            return SimpleHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_activity_list_item, parent, false))
        }

        override fun getItemCount(): Int {
            return list?.size?: 0
        }

        override fun onBindViewHolder(holder: SimpleHolder, position: Int) {
            val msg = getData(position)
            holder.itemSender.text = msg?.address
            holder.itemBody.text = msg?.body
        }

        fun getData(position: Int):Message?{
            return list?.get(position)
        }

    }
}
