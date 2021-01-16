package com.example.myshop.adapter.shop.home.category

import android.content.Context
import android.util.SparseArray
import androidx.databinding.ViewDataBinding
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.base.BaseAdapter
import com.example.myshop.base.IItemClick
import com.example.myshop.model.bean.shop.home.category.CategoryBean

class CategoryIssueAdapter(
    context: Context,
    list:List<CategoryBean.Issue>,
    layouts: SparseArray<Int>,
    var click: IItemClick<CategoryBean.Issue>
): BaseAdapter<CategoryBean.Issue>(context,list,layouts,click!!) {

    override fun layoutId(position: Int): Int {
        return R.layout.layout_category_issue_item
    }

    override fun bindData(binding: ViewDataBinding, data: CategoryBean.Issue, layId: Int) {
        binding.setVariable(BR.vmCategory_IssueClick,click)
    }

    fun refreshData(lt : List<CategoryBean.Issue>){
        list = lt
        notifyDataSetChanged()
    }
}