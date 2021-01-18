package com.example.myshop.adapter.shop.type

import android.content.Context
import android.util.SparseArray
import androidx.databinding.ViewDataBinding
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.base.BaseAdapter
import com.example.myshop.base.IItemClick
import com.example.myshop.model.bean.shop.type.DataX

class TypeInfoListAdapter(
    context: Context,
    list: List<DataX>,
    layouts: SparseArray<Int>,
    var click: IItemClick<DataX>
) : BaseAdapter<DataX>(context, list, layouts, click) {

    override fun layoutId(position: Int): Int {
        return R.layout.layout_type_info_list_item
    }

    override fun bindData(binding: ViewDataBinding, data: DataX, layId: Int) {
        binding.setVariable(BR.TypeInfoFragmentClick,click)
    }

    override fun bindIndex(position: Int) {
    }

    //刷新适配器
    fun refreshData(lt: List<DataX>){
        list = lt
        notifyDataSetChanged()
    }
}