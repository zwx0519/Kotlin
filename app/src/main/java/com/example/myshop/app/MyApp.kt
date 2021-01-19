package com.shop.app

import android.app.Application
import com.example.myshop.utils.MyMmkv

class MyApp: Application() {

    companion object{
        var instance:MyApp? = null
        @JvmField
        var app: MyApp? = null
        @JvmStatic
        var map: java.util.HashMap<String, Any>? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        map = HashMap()
        app = this
        map = HashMap()
        MyMmkv.initMMKV()
    }

}