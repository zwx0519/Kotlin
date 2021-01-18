package com.example.myshop.adapter.shop.type

import android.content.Context
import android.util.SparseArray
import androidx.databinding.ViewDataBinding
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.base.BaseAdapter
import com.example.myshop.base.BaseFragment
import com.example.myshop.base.IItemClick
import com.example.myshop.model.bean.shop.home.brand.ListData
import com.example.myshop.model.bean.shop.home.newgoods.GoodsList
import com.example.myshop.model.bean.shop.type.TypeInfoBean
import com.example.myshop.viewmodel.shop.type.TypeViewModel

class TypeInfoAdapter (context: Context,
                       list:List<TypeInfoBean.SubCategory>,
                       layouts: SparseArray<Int>,
                       var click: IItemClick<TypeInfoBean.SubCategory>)
    : BaseAdapter<TypeInfoBean.SubCategory>(context,list,layouts,click){

    override fun layoutId(position: Int): Int {
        return R.layout.layout_type_item
    }

    override fun bindData(binding: ViewDataBinding, data: TypeInfoBean.SubCategory, layId: Int) {
        binding.setVariable(BR.vmtypeinfoListClick,click)
    }
    override fun bindIndex(position: Int) {
    }

    //刷新适配器
    fun refreshData(lt: List<TypeInfoBean.SubCategory>){
        list = lt
        notifyDataSetChanged()
    }
}