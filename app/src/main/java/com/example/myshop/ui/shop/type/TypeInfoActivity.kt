package com.example.myshop.ui.shop.type

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import com.example.myshop.R
import com.example.myshop.databinding.ActivityTypeInfoBinding
import com.example.myshop.viewmodel.shop.type.TypeInfoViewModel
import com.shop.base.BaseActivity
import com.shop.utils.SpUtils
import kotlinx.android.synthetic.main.activity_type_info.*

class TypeInfoActivity (var lid: Int = R.layout.activity_type_info) :
    BaseActivity<TypeInfoViewModel, ActivityTypeInfoBinding>(lid, TypeInfoViewModel::class.java){
    var categoryId:Int ?=null
    var name :String ?=null

    override fun initView() {
        name=intent.getStringExtra("TypeName").toString()
    }

    override fun initVM() {
        categoryId= SpUtils.instance!!.getInt("TypeInfoId")
        mViewModel.getChannelType(categoryId!!)

    }

    override fun initData() {

    }

    override fun initVariable() {
        //初始化ViewModel
        //监听网络状态的变化
        mViewModel.getChannelType.observe(this, Observer { TypeInfoList->
            if (TypeInfoList != null && TypeInfoList.size > 0) {
                val fs = ArrayList<TypeInfoListFragment>()
                for (i in TypeInfoList.indices) {
                    val id = TypeInfoList[i].id
                    val name1 = TypeInfoList[i].name
                    val frontName = TypeInfoList[i].front_name

                    val typeInfoListFragment = TypeInfoListFragment(id,name1,frontName)

                    //添加进入fragment
                    fs.add(typeInfoListFragment)
                }

                //创建适配器
                mDataBinding.mVpTypeInfo!!.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
                    override fun getCount(): Int {
                        return fs.size
                    }

                    override fun getItem(position: Int): Fragment {
                        return fs[position]
                    }

                    override fun getPageTitle(position: Int): CharSequence? {
                        return TypeInfoList[position].name
                    }
                }
                mDataBinding.mTabTypeInfo!!.setupWithViewPager(mDataBinding.mVpTypeInfo)
                for (i in TypeInfoList.indices){
                    //如果获取的name == 集合的name
                    if (name == TypeInfoList[i].name) {
                        //被选中   select
                        mDataBinding.mTabTypeInfo!!.getTabAt(i)!!.select()
                    }
                }
            }


        })
    }

}