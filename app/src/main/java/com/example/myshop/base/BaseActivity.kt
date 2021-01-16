package com.shop.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin.base.IView

/**
 * baseactivity基类
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding>
    (var layoutId: Int, val vmClass: Class<VM>) : AppCompatActivity(),
    IView {
    protected lateinit var mViewModel: VM
    protected lateinit var mDataBinding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //DataBindingUtil 绑定视图
        mDataBinding = DataBindingUtil.setContentView(this, layoutId)
        //进行加载
        mViewModel = ViewModelProvider(this).get(vmClass)
        initView()
        initVM()
        initData()
        initVariable()
    }

    //TODO 加载视图
    protected abstract fun initView()

    //TODO ViewModel
    protected abstract fun initVM()

    //TODO 数据
    protected abstract fun initData()
    protected abstract fun initVariable()

    //TODO 页面数据
    override fun showLoading() {

    }

    //TODO 显示提示信息
    override fun showTips(tips: String) {
        Toast.makeText(this, tips, Toast.LENGTH_SHORT).show()
    }


}