package com.example.myshop.ui.shop.home.brand

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.databinding.ActivityBrandBinding
import com.example.myshop.viewmodel.shop.home.brand.BrandViewModel

class BrandActivity : AppCompatActivity() {
    var mBinding:ActivityBrandBinding? = null
    var vm: BrandViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_brand)
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_brand)
        initVM()
        
    }

    fun initVM(){
        vm = ViewModelProvider(this).get(BrandViewModel::class.java)
        vm !!.homebrandData()
        vm !!.stauts.observe(this, Observer {
            if (it == 0){
                mBinding!!.setVariable(BR.brand,vm)
            }
        })
    }
}