package com.example.myshop.viewmodel.shop.home.channel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshop.model.bean.shop.home.channel.ChannelBean
import com.example.myshop.model.bean.shop.home.channel.ChannelTypeCategory
import com.google.gson.Gson
import com.shop.utils.SpUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class ChannelViewModel : ViewModel() {
    //分类居家
    //https://cdplay.cn/api/catalog/index/pages/category/category?id=1005000
    //https://cdplay.cn/api/catalog/index?mur=url(传过来的url)

    // 定义tablayout  vp数据对象
    var category: MutableLiveData<List<ChannelTypeCategory>> = MutableLiveData()

    //网络请求的状态值  -1 网络请求错误
    var loadStatue:MutableLiveData<Int> = MutableLiveData()

    //TODO 加载数据
    fun loadChanneltypeData(){
        GlobalScope.launch {
            loadData()
        }
    }

    suspend fun loadData(){
        val url = SpUtils.instance!!.getStrring("home_url")
        var homechanneltypeData = get("https://cdplay.cn/api/catalog/index?mur="+url)
        if(homechanneltypeData != null){
            category.postValue(homechanneltypeData.data.categoryList)
        }else{
            loadStatue.postValue(-1)
        }
    }

    //网络请求
    suspend fun get(str:String) = withContext(Dispatchers.IO){
        val result = URL(str).readText(charset("utf-8"))
        Log.e("TAG", "loadData: "+result)
        return@withContext Gson().fromJson(result, ChannelBean::class.java)
    }
}