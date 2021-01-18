package com.example.myshop.adapter.shop.home.brand

import android.content.Context
import android.util.SparseArray
import androidx.databinding.ViewDataBinding
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.base.BaseAdapter
import com.example.myshop.base.IItemClick
import com.example.myshop.model.bean.shop.home.brand.DataX

class BrandNameAdapter (context: Context, list: List<DataX>, layouts: SparseArray<Int>, var click: IItemClick<DataX>?)
    : BaseAdapter<DataX>(context, list,layouts, click!!) {
    override fun layoutId(position: Int): Int {
        return R.layout.layout_brand_name_item
    }

    override fun bindData(binding: ViewDataBinding, data: DataX, layId: Int) {
        binding.setVariable(BR.BrandNameClick,click)
    }
    fun refreshData(lt : List<DataX>){
        list = lt
        notifyDataSetChanged()
    }

    override fun bindIndex(position: Int) {
    }
}