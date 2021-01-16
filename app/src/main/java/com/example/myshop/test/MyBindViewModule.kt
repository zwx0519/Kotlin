package com.example.myshop.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshop.model.bean.shop.home.HomeBean
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MyBindViewModule  : ViewModel(){
    var status: MutableLiveData<Int> = MutableLiveData()
    var title:String? = "title"

    fun homeData(){
        GlobalScope.launch {
            loadData()
        }
    }


    suspend fun loadData(){
        var homeData = get("https://cdplay.cn/api/index")
        if(homeData != null){
            title = homeData.data.hotGoodsList.get(0).name
            status.postValue(0)
        }
    }

    /**
     * 网络请求
     */
    suspend fun get(str:String) = withContext(Dispatchers.IO){
        var result = URL(str).readText(charset("utf-8"))
        return@withContext Gson().fromJson<HomeBean>(result, HomeBean::class.java)
    }


}