package com.example.myshop.adapter.shop.home.newgoods

import android.content.Context
import android.util.SparseArray
import androidx.databinding.ViewDataBinding
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.base.BaseAdapter
import com.example.myshop.base.IItemClick
import com.example.myshop.model.bean.shop.home.newgoods.GoodsList
import com.example.myshop.model.bean.shop.home.newgoods.NewGoodsListBean

class NewGoodsListAdapter (
    context: Context,
    list:List<GoodsList>,
    layouts: SparseArray<Int>,
    var click: IItemClick<GoodsList>
): BaseAdapter<GoodsList>(context,list,layouts,click) {

    override fun layoutId(position: Int): Int {
        return R.layout.layout_newgoods_item
    }

    override fun bindData(binding: ViewDataBinding, data: GoodsList, layId: Int) {
        binding.setVariable(BR.vmtypeinfoListClick,click)
    }

    fun refreshData(lt : List<GoodsList>){
        list = lt
        notifyDataSetChanged()
    }
}