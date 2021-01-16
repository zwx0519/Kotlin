package com.example.myshop.ui.shop.home.brand

import android.util.Log
import android.util.SparseArray
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.adapter.shop.home.brand.BrandNameDetailAdapter
import com.example.myshop.base.IItemClick
import com.example.myshop.databinding.ActivityBrandNameDetailBinding
import com.example.myshop.model.bean.shop.home.brand.ListData
import com.example.myshop.viewmodel.shop.home.brand.BrandNameDetailViewModel
import com.shop.base.BaseActivity
import com.shop.utils.SpUtils
import kotlinx.android.synthetic.main.activity_brand_name_detail.*

class BrandNameDetailActivity :
    BaseActivity<BrandNameDetailViewModel, ActivityBrandNameDetailBinding>
        (R.layout.activity_brand_name_detail, BrandNameDetailViewModel::class.java) {
    var mAdapter: BrandNameDetailAdapter? = null
    var list: List<ListData> = arrayListOf()
    var id: Int? = null

    //采用伴生对象 companion object==static
    companion object {
        val instance: BrandNameDetailActivity by lazy { BrandNameDetailActivity() }
    }

    override fun initView() {
        val layoutManager = GridLayoutManager(this, 2)
        mDataBinding!!.mRlvBrandNameDetail.layoutManager = layoutManager
    }

    override fun initVM() {
        id = SpUtils.instance!!.getInt("brand_id")
        Log.e("TAG", "id：" + id)

        if (id != null) {
            mViewModel.getBrandNameDetail(id!!)
            mViewModel.getBrandNameDetailList(id!!)
        } else {
            throw RuntimeException("ID不能为空")
        }

        //封装xml布局界面的id和界面中需要绑定的数据对应的id
        var array = SparseArray<Int>()
        array.put(R.layout.layout_brand_name_detail_item, BR.vmBrandNameDetailInfo)
        mAdapter = BrandNameDetailAdapter(this, list, array, ItemClickImpl())
        mDataBinding!!.mRlvBrandNameDetail.adapter = mAdapter

    }

    override fun initData() {

    }

    override fun initVariable() {
        mViewModel!!.brandnameList.observe(this, Observer {
            mAdapter!!.refreshData(it)
        })
        mViewModel!!.brandname.observe(this, Observer {
            mDataBinding.setVariable(BR.vmBrandNameDetail, it)
            Glide.with(this).load(it.app_list_pic_url).into(iv_brand_name_detail_img)
        })
    }

    //内部类
    inner class ItemClickImpl : IItemClick<ListData> {
        override fun itemClick(data: ListData?) {
            Log.i("TAG", data!!.name)
        }
    }
}