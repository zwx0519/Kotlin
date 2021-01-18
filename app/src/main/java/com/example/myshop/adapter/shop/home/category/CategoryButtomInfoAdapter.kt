package com.example.myshop.adapter.shop.home.category

import android.content.Context
import android.util.SparseArray
import androidx.databinding.ViewDataBinding
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.base.BaseAdapter
import com.example.myshop.base.IItemClick
import com.example.myshop.model.bean.shop.home.category.CategoryBean
import com.example.myshop.model.bean.shop.home.category.Goods

class CategoryButtomInfoAdapter(
    context: Context,
    list:List<Goods>,
    layouts: SparseArray<Int>,
    var click: IItemClick<Goods>
): BaseAdapter<Goods>(context,list,layouts,click) {

    override fun layoutId(position: Int): Int {
        return R.layout.layout_category_item
    }

    override fun bindData(binding: ViewDataBinding, data: Goods, layId: Int) {
        binding.setVariable(BR.Category_itemClick,click)
    }

    fun refreshData(lt : List<Goods>){
        list = lt
        notifyDataSetChanged()
    }

    override fun bindIndex(position: Int) {
    }
}