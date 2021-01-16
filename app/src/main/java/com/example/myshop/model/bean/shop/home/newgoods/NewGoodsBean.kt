package com.example.myshop.model.bean.shop.home.newgoods

data class NewGoodsBean(
    val bannerInfo: BannerInfo
)
data class BannerInfo(
    val img_url: String,
    val name: String,
    val url: String
)
