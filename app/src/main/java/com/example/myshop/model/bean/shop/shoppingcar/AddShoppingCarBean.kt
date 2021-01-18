package com.example.myshop.model.bean.shop.shoppingcar

data class AddShoppingCarBean(
    val cartList: List<Cart>,
    val cartTotal: CartTotal
)

data class CartTotal(
    val checkedGoodsAmount: Int,
    val checkedGoodsCount: Int,
    val goodsAmount: Int,
    val goodsCount: Int
)

data class Cart(
    val checked: Int,
    val goods_id: Int,
    val goods_name: String,
    val goods_sn: String,
    val goods_specifition_ids: String,
    val goods_specifition_name_value: String,
    val id: Int,
    val list_pic_url: String,
    val market_price: String,
    val number: Int,
    val product_id: Int,
    val retail_price: String,
    val session_id: String,
    val user_id: Int
)