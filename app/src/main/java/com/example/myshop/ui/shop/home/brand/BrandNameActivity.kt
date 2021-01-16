package com.example.myshop.ui.shop.home.brand

import android.content.Context
import android.content.Intent
import android.util.SparseArray
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.adapter.shop.home.brand.BrandNameAdapter
import com.example.myshop.base.IItemClick
import com.example.myshop.databinding.ActivityBrandBinding
import com.example.myshop.databinding.ActivityBrandNameBinding
import com.example.myshop.model.bean.shop.home.brand.DataX
import com.example.myshop.viewmodel.shop.home.brand.BrandNameViewModel
import com.shop.base.BaseActivity
import com.shop.utils.SpUtils

class BrandNameActivity :
    BaseActivity<BrandNameViewModel, ActivityBrandNameBinding>
        (
        R.layout.activity_brand_name,
        BrandNameViewModel::class.java
    ) {

    var context: Context? = null
    var mAdapter: BrandNameAdapter? = null
    var list: List<DataX> = arrayListOf()

    override fun initView() {
        context = this
        val layoutManager = LinearLayoutManager(this)
        mDataBinding!!.mRlvBrandName.layoutManager = layoutManager
    }

    override fun initVM() {
        mViewModel.getBrandName(1, 1000)

        //封装xml布局界面的id和界面中需要绑定的数据对应的id
        var array = SparseArray<Int>()
        array.put(R.layout.layout_brand_name_item, BR.vmBrandInfo)
        mAdapter = BrandNameAdapter(this, list, array, ItemClickImpl())
        mDataBinding!!.mRlvBrandName.adapter = mAdapter
    }

    override fun initData() {

    }

    override fun initVariable() {
        mViewModel!!.dataX.observe(this, Observer {
            mAdapter!!.refreshData(it)
        })
    }

    inner class ItemClickImpl : IItemClick<DataX> {
        override fun itemClick(data: DataX?) {
            //清空
            SpUtils.instance!!.remove("brand_id")
            SpUtils.instance!!.setValue("brand_id", data!!.id)
            val intent = Intent(context, BrandNameDetailActivity::class.java)
            startActivity(intent)
        }
    }

}