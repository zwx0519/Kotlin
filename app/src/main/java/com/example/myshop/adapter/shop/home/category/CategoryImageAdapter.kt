package com.example.myshop.adapter.shop.home.category

import android.content.Context
import android.util.SparseArray
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.base.BaseAdapter
import com.example.myshop.base.IItemClick
import com.shop.utils.SpUtils
import kotlinx.android.synthetic.main.layout_category_bigimage_item.view.*


class CategoryImageAdapter
    (
    context: Context,
    list: List<String>,
    layouts: SparseArray<Int>,
    var click: IItemClick<String>
) : BaseAdapter<String>(context, list, layouts, click) {

    override fun layoutId(position: Int): Int {
        return R.layout.layout_category_bigimage_item
    }

    override fun bindData(binding: ViewDataBinding, data: String, layId: Int) {
        Glide.with(context).load(data).into(binding.root.iv_bigimage_img)
        binding.setVariable(BR.Categoy_bigimageClick, click)
        SpUtils.instance!!.setValue("category_image",layId)
    }

}