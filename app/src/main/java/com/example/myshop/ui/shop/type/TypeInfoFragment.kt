package com.example.myshop.ui.shop.type

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.adapter.shop.type.TypeInfoAdapter
import com.example.myshop.base.BaseFragment
import com.example.myshop.base.IItemClick
import com.example.myshop.databinding.FragmentTypeInfoBinding
import com.example.myshop.model.bean.shop.home.newgoods.GoodsList
import com.example.myshop.model.bean.shop.type.TypeInfoBean
import com.example.myshop.ui.shop.home.category.CategoryActivity
import com.example.myshop.ui.shop.home.channel.ChannelActivity
import com.example.myshop.viewmodel.shop.type.TypeViewModel
import com.shop.utils.SpUtils
import kotlinx.android.synthetic.main.fragment_type_info.*

class TypeInfoFragment: BaseFragment<TypeViewModel, FragmentTypeInfoBinding>
    (R.layout.fragment_type_info, TypeViewModel::class.java){
    var categroy: TypeInfoAdapter? = null

    var list:MutableList<TypeInfoBean.SubCategory> = mutableListOf()

    //采用伴生对象 companion object==static
    companion object{
        val instance : TypeInfoFragment by lazy { TypeInfoFragment() }
    }

    override fun initView() {
        //返回顶部
        mDataBinding.mNsvType!!.fullScroll(ScrollView.FOCUS_UP)
        val layoutManager = GridLayoutManager(activity,3)
        //设置布局管理器
        mDataBinding!!.mRlvInfo.layoutManager = layoutManager

        //获取子条目布局
        var goodListArr = SparseArray<Int>()
        goodListArr.put(R.layout.layout_type_item, BR.vmtypeinfoList);

        //设置适配器
        categroy= TypeInfoAdapter(context!!,list,goodListArr,itemClick())
        //绑定适配器
        mDataBinding!!.mRlvInfo.adapter = categroy

    }


    inner class itemClick: IItemClick<TypeInfoBean.SubCategory> {
        override fun itemClick(data: TypeInfoBean.SubCategory?) {
            val intent = Intent(activity, TypeInfoActivity::class.java)
            var id =data!!.id
            SpUtils.instance!!.remove("TypeInfoId")
            SpUtils.instance!!.setValue("TypeInfoId", id)
            intent.putExtra("TypeName",data.name)
            startActivity(intent)
        }
    }

    override fun initVM() {
        mViewModel.gettype_info.observe(this, Observer {
            for (i in it.indices){
                Glide.with(context!!).load(it.get(i).banner_url).into(iv_typeinfo_head_img)

                tv_typeinfo_head_desc.setText(it.get(i).front_desc)
                tv_typeinfo_title.setText(it.get(i).name+"分类")

                categroy!!.refreshData(it.get(i).subCategoryList)//进行加载数据
            }
        })

    }

    override fun initData() {
        val id = getArguments()?.getInt("id")
        if (id != null) {
            mViewModel.getTypeInfo(id)
        }
    }

    override fun initVariable() {

    }


}