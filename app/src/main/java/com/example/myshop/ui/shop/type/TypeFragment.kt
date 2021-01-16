package com.example.myshop.ui.shop.type

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.myshop.R
import com.example.myshop.adapter.shop.type.TypeAdapter
import com.example.myshop.base.BaseFragment
import com.example.myshop.databinding.FragmentTypeBinding
import com.example.myshop.model.bean.shop.type.Category
import com.example.myshop.viewmodel.shop.type.TypeViewModel
import kotlinx.android.synthetic.main.fragment_type.*

class TypeFragment:BaseFragment<TypeViewModel, FragmentTypeBinding>
    (R.layout.fragment_type,
    TypeViewModel::class.java)  {

    //采用伴生对象 companion object==static
    companion object{
        val instance : TypeFragment by lazy { TypeFragment() }
    }

    override fun initView() {
        //禁止滑动
        mVp_type.setScanScroll(false)
    }

    override fun initVM() {
        val fragments = ArrayList<TypeInfoFragment>()
        if(!isAdded)return

        mViewModel.gettype.observe(this, Observer {categroy ->
            for (i  in categroy.indices){
                var id = categroy.get(i).id
                var typeInfoFragment = TypeInfoFragment()
                var bundle = Bundle()
                bundle.putInt("id",id)
                typeInfoFragment.arguments = bundle
                fragments.add(typeInfoFragment)
            }
            var typeAdapter = TypeAdapter(childFragmentManager)
            mVp_type.adapter = typeAdapter
            typeAdapter.addList(fragments, categroy as ArrayList<Category>)
            mTab_type.setupWithViewPager(mVp_type)
        })
    }

    override fun initData() {
        mViewModel.getType()
    }

    override fun initVariable() {

    }


}