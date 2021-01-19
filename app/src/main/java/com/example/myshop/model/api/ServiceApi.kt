package com.example.myshop.model.api

import com.example.basemvvm.model.bean.me.MeLoginBean
import com.example.basemvvm.model.bean.me.MeRegisterBean
import com.example.myshop.model.bean.shop.home.HomeBean
import com.example.myshop.model.bean.shop.home.brand.BrandNameBean
import com.example.myshop.model.bean.shop.home.brand.BrandNameDetailBean
import com.example.myshop.model.bean.shop.home.brand.BrandNameDetailListBean
import com.example.myshop.model.bean.shop.home.category.CategoryBean
import com.example.myshop.model.bean.shop.home.category.CategoryBottomInfoBean
import com.example.myshop.model.bean.shop.home.newgoods.NewGoodsBean
import com.example.myshop.model.bean.shop.home.newgoods.NewGoodsListBean
import com.example.myshop.model.bean.shop.me.login.MeUserInfoBean
import com.example.myshop.model.bean.shop.shoppingcar.AddShoppingCarBean
import com.example.myshop.model.bean.shop.shoppingcar.DeleteShoppingCarBean
import com.example.myshop.model.bean.shop.shoppingcar.ShoppingCarBean
import com.example.myshop.model.bean.shop.shoppingcar.UpdateShoppingCarBean
import com.example.myshop.model.bean.shop.special.SpecialBean
import com.example.myshop.model.bean.shop.type.TypeBean
import com.example.myshop.model.bean.shop.type.TypeInfoBean
import com.example.myshop.model.bean.shop.type.TypeInfoButtomBean
import com.example.myshop.model.bean.shop.type.TypeInfoTopBean
import com.example.myshop.model.bean.test.more_view.More_ViewBean
import com.shop.net.BaseResp
import retrofit2.http.*
import java.util.*

interface ServiceApi {
    //刷新token
    @POST("auth/refreshToken")
    suspend fun refreshToken(): BaseResp<String>

    //首页 // BaseResp抽取的一个bean类的外层结构 homeData当前接口返回的具体
    @GET("index")
    suspend fun getHome(): BaseResp<HomeBean>

    //居家 商品详情购买页        1155000
    @GET("goods/detail")
    suspend fun getCategory(@Query("id") id: Int): BaseResp<CategoryBean>

    //商品 详情购买页 底部数据列表
    @GET("goods/related")
    suspend fun getCategoryBottomInfo(@Query("id") id: Int): BaseResp<CategoryBottomInfoBean>

    //品牌制造
    @GET("brand/list")
    suspend fun getBrandName(@Query("page") page: Int, @Query("size") size: Int): BaseResp<BrandNameBean>

    //品牌制造商列表详情
    @GET("brand/detail")
    suspend fun getBrandNameDetail(@Query("id") id: Int): BaseResp<BrandNameDetailBean>

    //品牌制造商列表详情条目
    @GET("goods/list")
    suspend fun getBrandNameDetailList(@Query("brandId") brandId: Int): BaseResp<BrandNameDetailListBean>

    //新品首发列表详情
    @GET("goods/list")
    suspend fun getNewGoodsList(@QueryMap map: HashMap<String, String>):BaseResp<NewGoodsListBean>

    //新品首发
    @GET("goods/hot")
    suspend fun getNewGoods():BaseResp<NewGoodsBean>

    //专题
    @GET("topic/list")
    suspend fun getSpecial(@Query("page" )page: Int): BaseResp<SpecialBean>

    //分类竖着导航
    @GET("catalog/index")
    suspend fun getType() :BaseResp<TypeBean>

    //分类列表数据
    @GET("catalog/current")
    suspend fun getTypeInfo(@Query("id")id : Int) : BaseResp<TypeInfoBean>

    //分类Tab标题数据 catalog/index/pages/category/category?id=1005001
    @GET("goods/category")
    suspend fun getChannelType(@Query("id")id : Int) : BaseResp<TypeInfoTopBean>

    //分类右边数据点击详情rlv
    @GET("goods/list?page=1&size=100")
    suspend fun getChannelTypeInfo(@Query("categoryId") id: Int): BaseResp<TypeInfoButtomBean>

    //添加到购物车
    @POST("cart/add")
    @FormUrlEncoded
    suspend fun postAddShoppingCar(@FieldMap map: HashMap<String?, String?>?): BaseResp<AddShoppingCarBean>

    //更新购物车的数据
    @POST("cart/update") //  goodsId=1035006 number=1   productId=47     // 1116033   1   171
    @FormUrlEncoded
    suspend fun postUpdateShoppingCar(@FieldMap map: Map<String?, String?>?): BaseResp<UpdateShoppingCarBean>

    //删除购物车数据
    @POST("cart/delete")
    @FormUrlEncoded
    suspend  fun postDeleteShoppingCar(@Field("productIds") productIds: String?): BaseResp<DeleteShoppingCarBean>

    //购物车列表
    @GET("cart/index")
    suspend fun getShoppingCar(): BaseResp<ShoppingCarBean>

    //多布局的接口
    @GET("discover/hot.json")
    suspend fun getMore(): More_ViewBean

    //登录接口
    @POST("auth/login")
    @FormUrlEncoded
    suspend fun MeLogin(@Field("username")username:String,@Field("password")password:String):BaseResp<MeLoginBean>

    //注册接口
    @POST("auth/register")
    @FormUrlEncoded
    suspend fun MeRegist(@Field("username")username:String,@Field("password")password:String):BaseResp<MeRegisterBean>

    //用户信息更新
    @POST("/user/updateUserInfo")
    @FormUrlEncoded
    suspend fun MeUserInfo(@FieldMap map: HashMap<String, String>):BaseResp<MeUserInfoBean>

}