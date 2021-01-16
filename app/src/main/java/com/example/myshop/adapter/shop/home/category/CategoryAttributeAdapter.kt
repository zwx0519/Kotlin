package com.example.myshop.adapter.shop.home.category

import android.content.Context
import android.util.SparseArray
import androidx.databinding.ViewDataBinding
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.base.BaseAdapter
import com.example.myshop.base.IItemClick
import com.example.myshop.model.bean.shop.home.category.CategoryBean
import com.example.myshop.model.bean.shop.home.newgoods.GoodsList

class CategoryAttributeAdapter(
    context: Context,
    list:List<CategoryBean.Attribute>,
    layouts: SparseArray<Int>,
    var click: IItemClick<CategoryBean.Attribute>
): BaseAdapter<CategoryBean.Attribute>(context,list!!,layouts,click!!) {

    override fun layoutId(position: Int): Int {
        return R.layout.layout_category_attribute_item
    }

    override fun bindData(binding: ViewDataBinding, data: CategoryBean.Attribute, layId: Int) {
        binding.setVariable(BR.Category_AttributeClick,click)
    }
    fun refreshData(lt : List<CategoryBean.Attribute>){
        list = lt
        notifyDataSetChanged()
    }
}