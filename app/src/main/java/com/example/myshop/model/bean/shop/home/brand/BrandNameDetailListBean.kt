package com.example.myshop.model.bean.shop.home.brand

data class BrandNameDetailListBean(
    val count: Int,
    val currentPage: Int,
    val `data`: List<ListData>,
    val filterCategory: List<FilterCategory>,
    val goodsList: List<Goods>,
    val pageSize: Int,
    val totalPages: Int
)

data class FilterCategory(
    val checked: Boolean,
    val id: Int,
    val name: String
)

data class Goods(
    val id: Int,
    val list_pic_url: String,
    val name: String,
    val retail_price: Int
)

data class ListData(
    val id: Int,
    val list_pic_url: String,
    val name: String,
    val retail_price: String
)