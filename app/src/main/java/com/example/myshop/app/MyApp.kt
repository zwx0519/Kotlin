package com.shop.app

import android.app.Application
import com.example.myshop.utils.MyMmkv

class MyApp: Application() {

    companion object{
        var instance:MyApp? = null
        var map:HashMap<String, Any>? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        map = HashMap()

        MyMmkv.initMMKV()
    }

    fun getMap(): HashMap<String, Any>? {
        return map
    }

}