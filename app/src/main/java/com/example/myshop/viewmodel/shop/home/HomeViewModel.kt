package com.example.myshop.viewmodel.shop.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshop.model.bean.shop.home.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class HomeViewModel : ViewModel() {
    //采用MutableLiveData进行修饰 并进行实例化

    // 定义轮播图数据对象
    var banner: MutableLiveData<List<Banner>> = MutableLiveData()

    // 定义一个动态栏
    var channel: MutableLiveData<List<Channel>> = MutableLiveData()

    // 定义一个品牌制造商直供
    var brand: MutableLiveData<List<Brand>> = MutableLiveData()

    //定义一个新品首发
    var newGoods: MutableLiveData<List<NewGoods>> = MutableLiveData()

    // 定义一个人气推荐
    var hotGoods: MutableLiveData<List<HotGoods>> = MutableLiveData()

    // 定义一个专题精选
    var topic: MutableLiveData<List<Topic>> = MutableLiveData()

    // 定义一个居家
    var category: MutableLiveData<List<Category>> = MutableLiveData()

    //网络请求的状态值  -1 网络请求错误
    var loadStatue: MutableLiveData<Int> = MutableLiveData()

    //TODO 加载首页数据
    fun loadHomeData() {
        GlobalScope.launch {
            loadData()
        }
    }

    //TODO 协程
    suspend fun loadData() {
        var homeData = get("https://cdplay.cn/api/index")
        if (homeData != null) {
            //postValue做线程切换 协程里面是子线程
            banner.postValue(homeData.data.banner)//轮播图数据
            channel.postValue(homeData.data.channel)//轮播图数据
            brand.postValue(homeData.data.brandList)//品牌制造商直供
            newGoods.postValue(homeData.data.newGoodsList)//新品首发
            hotGoods.postValue(homeData.data.hotGoodsList)//人气推荐
            topic.postValue(homeData.data.topicList)//专题精选
            category.postValue(homeData.data.categoryList)//居家
        } else {
            loadStatue.postValue(-1)
        }
    }

    //TODO 网络请求
    suspend fun get(str: String) = withContext(Dispatchers.IO) {
        var result = URL(str).readText(charset("utf-8"))
        return@withContext Gson().fromJson<HomeBean>(result, HomeBean::class.java)
    }
}