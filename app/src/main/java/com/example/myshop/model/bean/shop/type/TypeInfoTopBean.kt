package com.example.myshop.model.bean.shop.type

data class TypeInfoTopBean(
    val brotherCategory: List<BrotherCategory>,
    val currentCategory: CurrentCategoryInfo,
    val parentCategory: ParentCategory
) {
    data class BrotherCategory(
        val banner_url: String,
        val front_desc: String,
        val front_name: String,
        val icon_url: String,
        val id: Int,
        val img_url: String,
        val is_show: Int,
        val keywords: String,
        val level: String,
        val name: String,
        val parent_id: Int,
        val show_index: Int,
        val sort_order: Int,
        val type: Int,
        val wap_banner_url: String
    )

    data class CurrentCategoryInfo(
        val banner_url: String,
        val front_desc: String,
        val front_name: String,
        val icon_url: String,
        val id: Int,
        val img_url: String,
        val is_show: Int,
        val keywords: String,
        val level: String,
        val name: String,
        val parent_id: Int,
        val show_index: Int,
        val sort_order: Int,
        val type: Int,
        val wap_banner_url: String
    )

    data class ParentCategory(
        val banner_url: String,
        val front_desc: String,
        val front_name: String,
        val icon_url: String,
        val id: Int,
        val img_url: String,
        val is_show: Int,
        val keywords: String,
        val level: String,
        val name: String,
        val parent_id: Int,
        val show_index: Int,
        val sort_order: Int,
        val type: Int,
        val wap_banner_url: String
    )
}
