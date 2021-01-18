package com.example.myshop.adapter.shop.home.newgoods

import android.content.Context
import android.util.SparseArray
import androidx.databinding.ViewDataBinding
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.base.BaseAdapter
import com.example.myshop.base.IItemClick
import com.example.myshop.model.bean.shop.home.newgoods.FilterCategory
import com.example.myshop.model.bean.shop.home.newgoods.GoodsList

class NewGoodsPopuAdapter (
    context: Context,
    list:List<FilterCategory>,
    layouts: SparseArray<Int>,
    var click: IItemClick<FilterCategory>
): BaseAdapter<FilterCategory>(context,list,layouts,click!!){

    override fun layoutId(position: Int): Int {
        return R.layout.layout_newgoods_popw_item
    }

    override fun bindData(binding: ViewDataBinding, data: FilterCategory, layId: Int) {
        binding.setVariable(BR.newgoods_popuClick,click)
    }

    override fun bindIndex(position: Int) {
    }


    fun refreshData(lt : List<FilterCategory>){
        list = lt
        notifyDataSetChanged()
    }

}