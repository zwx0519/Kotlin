package com.example.myshop.net.repository

import com.example.myshop.model.api.ServiceApi
import com.shop.net.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.HashMap

/**
 * 数据仓库
 * 用来连接ViewModel和Model
 * 定义业务相关的网络请求接口的api
 */
class SystemRepository {

    private lateinit var serviceApi: ServiceApi

    //TODO 初始化的方法
    init {
        //在这里调用create 创建ServiceApi进行实例化
        serviceApi = RetrofitFactory.instance.create(ServiceApi::class.java)

    }

    //TODO 希望在协程里面进行 所以使用suspend修饰

    //TODO 刷新token
    suspend fun refreshToken() = withContext(Dispatchers.IO) {
        serviceApi.refreshToken()
    }

    //TODO 获取主页数据
    suspend fun getHome() = withContext(Dispatchers.IO) {
        serviceApi.getHome()
    }

    //TODO 获取专题数据
    suspend fun getSpecial(page: Int) = withContext(Dispatchers.IO) {
        serviceApi.getSpecial(page)
    }

    //TODO 品牌制造商列表
    suspend fun getBrandName(page: Int, size: Int) = withContext(Dispatchers.IO) {
        serviceApi.getBrandName(page, size)
    }

    //TODO 品牌制造商列表详情
    suspend fun getBrandNameDetail(id: Int) = withContext(Dispatchers.IO) {
        serviceApi.getBrandNameDetail(id)
    }

    //TODO 品牌制造商列表详情列表
    suspend fun getBrandNameDetailList(brandId: Int) = withContext(Dispatchers.IO) {
        serviceApi.getBrandNameDetailList(brandId)
    }

    //TODO 新品首发列表详情列表
    suspend fun getNewGoodsList(map: HashMap<String, String>) = withContext(Dispatchers.IO) {
        serviceApi.getNewGoodsList(map)
    }

    //TODO 新品首发
    suspend fun getNewGoods() = withContext(Dispatchers.IO) {
        serviceApi.getNewGoods()
    }

    //TODO 居家 商品详情购买页
    suspend fun getCategory(id: Int) = withContext(Dispatchers.IO) {
        serviceApi.getCategory(id)
    }

    //TODO 商品 详情购买页 底部数据列表
    suspend fun getCategoryBottomInfo(id: Int) = withContext(Dispatchers.IO) {
        serviceApi.getCategoryBottomInfo(id)
    }

    //TODO 分类竖着导航
    suspend fun getType() = withContext(Dispatchers.IO) {
        serviceApi.getType()
    }

    //TODO 分类列表数据
    suspend fun getTypeInfo(id:Int) = withContext(Dispatchers.IO) {
        serviceApi.getTypeInfo(id)
    }

    //TODO 分类Tab标题数据
    suspend fun getChannelType(category:Int) = withContext(Dispatchers.IO) {
        serviceApi.getChannelType(category)
    }

    //TODO 分类Vage列表数据
    suspend fun getChannelTypeInfo(category:Int) = withContext(Dispatchers.IO) {
        serviceApi.getChannelTypeInfo(category)
    }

    //TODO 购物车列表数据
    suspend fun getShoppingCar() = withContext(Dispatchers.IO) {
        serviceApi.getShoppingCar()
    }

    //TODO 获取多布局数据
    suspend fun getMore() = withContext(Dispatchers.IO) {
        serviceApi.getMore()
    }
}