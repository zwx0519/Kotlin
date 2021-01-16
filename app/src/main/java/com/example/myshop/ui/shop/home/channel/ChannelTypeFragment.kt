package com.example.myshop.ui.shop.home.channel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myshop.R
import com.example.myshop.adapter.shop.home.channel.ChannelTypeAdapter
import com.example.myshop.model.bean.shop.home.channel.ChannelTreeDataX
import com.example.myshop.test.MyItemClick
import com.example.myshop.ui.shop.home.brand.BrandActivity
import com.example.myshop.ui.shop.home.category.CategoryActivity
import com.example.myshop.viewmodel.shop.home.channel.ChannelTypeViewModel
import com.shop.utils.SpUtils
import kotlinx.android.synthetic.main.fragment_channel_type.*

class   ChannelTypeFragment : Fragment() {

    lateinit var homeVM: ChannelTypeViewModel
    var title1:String? = null
    var front_name:String? = null

    var id:Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_channel_type, container, false)

        id = arguments!!.getInt("channel_id")
        title1 = arguments!!.getString("channel_name")
        front_name = arguments!!.getString("front_name")
        //先清空SP
        SpUtils.instance!!.remove("channel_id")
        //Sp保存id给ViewModel
        SpUtils.instance!!.setValue("channel_id",id)
        initVm()
        homeVM.loadChanneltreeData()

        return inflate
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("TAG","title:"+title1+"   "+front_name)

        tv_channel1_title!!.setText(title1)
        tv_channel1_front_desc!!.setText(front_name)
    }

    fun initVm(){
        //初始化ViewModel
        homeVM = ViewModelProvider(this).get(ChannelTypeViewModel::class.java)
        //监听网络状态的变化
        homeVM.loadStatue.observe(this, Observer { status ->
            if (status == -1) {
                Log.e("TAG", "HomeChannelTreeFragment: "+"数据加载失败" )
            }
        })

        //监听底部rlv数据的变化
        homeVM.dataX.observe(this, Observer {
            //网格布局
            mRlv_channelType!!.layoutManager = GridLayoutManager(activity, 2)
            //分割线
            mRlv_channelType!!.addItemDecoration(DividerItemDecoration(activity, LinearLayout.VERTICAL))

            //val 不可变参数
            val data = arrayListOf<ChannelTreeDataX>()
            //添加值
            data.addAll(it)
            //设置适配器
            val homeTreeAdapter = ChannelTypeAdapter(data, activity)
            //绑定适配器
            mRlv_channelType!!.adapter = homeTreeAdapter

            //条目点击
            homeTreeAdapter!!.setOnItemClick(object :MyItemClick{
                override fun onItemCilck(pos: Int) {
                    val intent = Intent(activity, CategoryActivity::class.java)
                    var id = it.get(pos)!!.id
                    SpUtils.instance!!.remove("Category_id")
                    SpUtils.instance!!.setValue("Category_id", id)
                    startActivity(intent)
                }
            })
        })
    }
}