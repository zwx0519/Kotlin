package com.example.myshop.ui.shop.type

import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.adapter.shop.home.brand.BrandNameDetailAdapter
import com.example.myshop.adapter.shop.special.SpecialAdapter
import com.example.myshop.adapter.shop.type.TypeInfoListAdapter
import com.example.myshop.base.BaseFragment
import com.example.myshop.base.IItemClick
import com.example.myshop.databinding.FragmentTypeInfoListBinding
import com.example.myshop.model.bean.shop.home.brand.ListData
import com.example.myshop.model.bean.shop.type.DataX
import com.example.myshop.viewmodel.shop.type.TypeInfoListViewModel

class TypeInfoListFragment (var mid:Int,var mName:String,var mFront_name:String):
    BaseFragment<TypeInfoListViewModel, FragmentTypeInfoListBinding>(
        R.layout.fragment_type_info_list,
        TypeInfoListViewModel::class.java) {

    var list: List<DataX> = arrayListOf()
    override fun initView() {
        mDataBinding.tvTypeInfoTitle.setText(mName)
        mDataBinding.tvTypeInfoFrontDesc.setText(mFront_name)

        //设置布局管理器
        mDataBinding.mRlvTypeInfoList.layoutManager = GridLayoutManager(context,2)

    }

    override fun initVM() {
        mViewModel.getChannelTypeInfo(mid)

    }

    override fun initData() {
        mViewModel.getChannelTypeInfo.observe(this, Observer {
            var sortArr = SparseArray<Int>()
            sortArr.put(R.layout.layout_type_info_list_item,BR.vmTypeInfoItem)
            mDataBinding!!.mRlvTypeInfoList.adapter = TypeInfoListAdapter(context!!, it, sortArr, SortDataInfoClik())
            //mAdapter!!.refreshData(it)
        })
    }

    override fun initVariable() {

    }

    inner class SortDataInfoClik: IItemClick<DataX> {
        override fun itemClick(data: DataX?) {
            Log.e("TAG", "itemClick: "+data!!.name )
        }

    }
}