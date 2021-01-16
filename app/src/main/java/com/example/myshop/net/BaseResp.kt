package com.shop.net

//data class BaseResp<out T>(val errno:Int,val errmsg:Int,val data:T)

//TODO 使用泛型接收不同的对应数据
data class BaseResp<T>
constructor(var errno:Int,var errmsg:String,var data:T)
