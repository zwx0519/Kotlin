package com.example.myshop.test.more_view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.base.IItemClick
import com.example.myshop.databinding.ActivityMoreViewBinding
import com.example.myshop.model.bean.test.more_view.Stu
import com.example.myshop.viewmodel.shop.home.HomeViewModel
import com.shop.base.BaseActivity
import kotlinx.android.synthetic.main.activity_more_view.*
import kotlinx.android.synthetic.main.fragment_home.*

class More_ViewActivity : BaseActivity<MoreViewModel, ActivityMoreViewBinding>
    (R.layout.activity_more_view, MoreViewModel::class.java) {

    //lateinit 延迟初始化
    lateinit var adapter: More_ViewAdapter
    var list: List<Stu> = arrayListOf()

    override fun initView() {
        //设置布局管理器
        val layoutManager = LinearLayoutManager(this)
        mDataBinding!!.mRlvMoreView.layoutManager = layoutManager

        //分割线
        mRlv_More_View!!.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
    }


    override fun initVM() {
        //观察者模式
        mViewModel.stu.observe(this, Observer {
            adapter!!.refreshData(it)
        })
    }

    override fun initData() {
        mViewModel.getMore()

        //封装xml布局界面的id和界面中需要绑定的数据对应的id
        var array = SparseArray<Int>()
        array.put(R.layout.layout_more_view_item, BR.vmMore_View)
        array.put(R.layout.layout_more_view_item_one, BR.vmMore_View_one)
        array.put(R.layout.layout_more_view_item_two, BR.vmMore_View_two)

        adapter = More_ViewAdapter(this, list, array,ItemClickImpl())
        //绑定适配器
        mDataBinding!!.mRlvMoreView.adapter = adapter

    }

    override fun initVariable() {

    }

    inner class ItemClickImpl: IItemClick<Stu> {
        override fun itemClick(data: Stu?) {
            Log.i("TAG",data!!.title)
        }
    }
}