package com.example.myshop.ext

import android.app.Activity
import android.view.View

/**
 * View扩展 FindViewById的操作
 */
//fun <V> View.findView(id: Int):Lazy<V> = lazy {
//    findViewById(id)
//}

fun <V:View> findView(id:Int): Lazy<Unit> = lazy {
    findViewById(id)
}

fun findViewById(id: Int) {
    TODO("Not yet implemented")
}
