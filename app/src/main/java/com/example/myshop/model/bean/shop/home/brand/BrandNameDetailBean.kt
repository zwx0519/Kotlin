package com.example.myshop.model.bean.shop.home.brand

data class BrandNameDetailBean(
    val brand: Brand
)

data class Brand(
    val app_list_pic_url: String,
    val floor_price: String,
    val id: Int,
    val is_new: Int,
    val is_show: Int,
    val list_pic_url: String,
    val name: String,
    val new_pic_url: String,
    val new_sort_order: Int,
    val pic_url: String,
    val simple_desc: String,
    val sort_order: Int
)