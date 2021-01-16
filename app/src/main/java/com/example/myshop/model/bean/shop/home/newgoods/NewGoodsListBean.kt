package com.example.myshop.model.bean.shop.home.newgoods

data class NewGoodsListBean(
    val count: Int,
    val currentPage: Int,
    val data: List<Data>,
    val filterCategory: List<FilterCategory>,
    val goodsList: List<GoodsList>,
    val pageSize: Int,
    val totalPages: Int
)

data class FilterCategory(
    val checked: Boolean,
    val id: Int,
    val name: String
)

data class GoodsList(
    val id: Int,
    val list_pic_url: String,
    val name: String,
    val retail_price: String
)

data class Data(
    val id: Int,
    val list_pic_url: String,
    val name: String,
    val retail_price: String
)