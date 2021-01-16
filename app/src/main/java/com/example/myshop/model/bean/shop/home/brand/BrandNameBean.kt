package com.example.myshop.model.bean.shop.home.brand

data class BrandNameBean(
    val count: Int,
    val currentPage: Int,
    val `data`: List<DataX>,
    val pageSize: Int,
    val totalPages: Int
)

data class DataX(
    val app_list_pic_url: String,
    val floor_price: String,
    val id: Int,
    val name: String
)