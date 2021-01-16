package com.example.myshop.viewmodel.shop.home.brand

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshop.model.bean.shop.home.brand.BrandBean
import com.google.gson.Gson
import com.shop.utils.SpUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class BrandViewModel: ViewModel() {
    var stauts: MutableLiveData<Int> = MutableLiveData()
    var tv_brand_name:String? = "tv_brand_name"
    var tv_brand_simple:String? = "tv_brand_simple"

    fun homebrandData(){
        GlobalScope.launch {
            loadData()
        }
    }

    suspend fun loadData(){
        var id = SpUtils.instance!!.getInt("Brand_id")
        Log.e("TAG", "loadData: "+id)
        var homebranddata = get("https://cdplay.cn/api/brand/detail?id="+id)
        if(homebranddata != null){
            tv_brand_name = homebranddata.data.brand.name
            tv_brand_simple = homebranddata.data.brand.simple_desc

            stauts.postValue(0)
        }
    }

    //网络请求
    suspend fun get(str:String) = withContext(Dispatchers.IO){
        val result = URL(str).readText(charset("utf-8"))
        Log.e("TAG", "loadData: "+result)
        return@withContext Gson().fromJson(result, BrandBean::class.java)
    }


}