package com.example.myshop.adapter.shop.home.brand

import android.content.Context
import android.util.SparseArray
import androidx.databinding.ViewDataBinding
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.base.BaseAdapter
import com.example.myshop.base.IItemClick
import com.example.myshop.model.bean.shop.home.brand.ListData
import com.example.myshop.model.bean.shop.home.category.CategoryBean
import com.example.myshop.model.bean.shop.home.category.Goods

class BrandNameDetailAdapter(
    context: Context, list: List<ListData>,
    layouts: SparseArray<Int>,
    var click: IItemClick<ListData>)
    : BaseAdapter<ListData>(context, list,layouts,click){

    override fun layoutId(position: Int): Int {
        return R.layout.layout_brand_name_detail_item
    }

    override fun bindData(binding: ViewDataBinding, data: ListData, layId: Int) {
        binding.setVariable(BR.BrandNameDetailClick,click)
    }

    fun refreshData(lt : List<ListData>){
        list = lt
        notifyDataSetChanged()
    }

    override fun bindIndex(position: Int) {
    }
}