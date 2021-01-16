package com.example.myshop.viewmodel.shop.home.channel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshop.model.bean.shop.home.channel.ChannelTreeDataX
import com.example.myshop.model.bean.shop.home.channel.ChannelTypeBean
import com.google.gson.Gson
import com.shop.utils.SpUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class ChannelTypeViewModel : ViewModel() {
    //分类数据居家
    //https://cdplay.cn/api/goods/list?page=1&size=100&categoryId=1005007

    // 定义rlv数据对象
    var dataX: MutableLiveData<List<ChannelTreeDataX>> = MutableLiveData()

    //网络请求的状态值  -1 网络请求错误
    var loadStatue: MutableLiveData<Int> = MutableLiveData()

    //TODO 加载数据
    fun loadChanneltreeData(){
        GlobalScope.launch {
            loadData()
        }
    }

    suspend fun loadData(){
        val id = SpUtils.instance!!.getInt("channel_id")
        var channeltreeData = get("https://cdplay.cn/api/goods/list?page=1&size=100&categoryId="+id)
        if(channeltreeData != null){
            dataX.postValue(channeltreeData.data.data)
        }else{
            loadStatue.postValue(-1)
        }
    }

    //网络请求
    suspend fun get(str:String) = withContext(Dispatchers.IO){
        val result = URL(str).readText(charset("utf-8"))
        return@withContext Gson().fromJson(result, ChannelTypeBean::class.java)
    }
}