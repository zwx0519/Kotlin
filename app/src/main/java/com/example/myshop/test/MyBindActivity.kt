package com.example.myshop.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.databinding.ActivityMyBindBinding

class MyBindActivity : AppCompatActivity() {
    //拿到的binding的对象，用来调用界面上的id组件
    var mBinding :ActivityMyBindBinding ? = null
    //实例化ViewModel,通过ViewModelProvider初始化VM
    var vm:MyBindViewModule? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_my_bind)
        //V层通过DataBindingUtil初始化界面 并且获取绑定返回的binding对象
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_my_bind)
        initVM()
    }

    fun initVM(){
        //实例化ViewModel,通过ViewModelProvider初始化VM
        vm = ViewModelProvider(this).get(MyBindViewModule::class.java)
        //对VM中的变量进行赋值
        vm!!.homeData()
        vm!!.status.observe(this, Observer {
            if(it == 0){
                //通过binding对象设置数据到界面
                mBinding!!.setVariable(BR.bindNewVm,vm)
            }
        })
    }
}