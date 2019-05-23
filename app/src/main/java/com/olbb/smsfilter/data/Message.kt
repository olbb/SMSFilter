package com.olbb.smsfilter.data

data class Message(val id:Int, val address:String, val body:String,val date:Long,var person:String?, val type:Int)