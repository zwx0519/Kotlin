package com.example.myshop.model.bean.shop.home.category


data class CategoryBottomInfoBean(
    val goodsList: List<Goods>
)

data class Goods(
    val id: Int,
    val list_pic_url: String,
    val name: String,
    val retail_price: String
)