package com.example.myshop.ui.shop.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshop.R
import com.example.myshop.adapter.shop.ShopAdapter
import com.example.myshop.adapter.shop.home.brand.BrandAdapter
import com.example.myshop.adapter.shop.home.category.CategoryAdapter
import com.example.myshop.adapter.shop.home.hotgoods.HotGoodsAdapter
import com.example.myshop.adapter.shop.home.newgoods.NewGoodsAdapter
import com.example.myshop.adapter.shop.home.topic.TopicAdapter
import com.example.myshop.test.MyItemClick
import com.example.myshop.model.bean.shop.home.Banner
import com.example.myshop.viewmodel.shop.home.HomeViewModel
import com.example.myshop.ui.shop.home.brand.BrandActivity
import com.example.myshop.ui.shop.home.brand.BrandNameActivity
import com.example.myshop.ui.shop.home.category.CategoryActivity
import com.example.myshop.ui.shop.home.channel.ChannelActivity
import com.example.myshop.ui.shop.home.newgoods.NewGoodsActivity
import com.shop.utils.SpUtils
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_channel_item.view.*
import kotlinx.android.synthetic.main.layout_home_item.view.*

class HomeFragment : Fragment(), View.OnClickListener {

    //lateinit 延迟初始化
    lateinit var homeViewVM: HomeViewModel

    //采用伴生对象 companion object==static 只能创建一次
    companion object {
        val instance by lazy { HomeFragment() }
    }


    //TODO 加载视图
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    //TODO 初始化数据
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVm()
        homeViewVM.loadHomeData()//加载首页数据

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListener()//点击监听
        initMarquee()
    }

    //TODO 实现跑马灯效果
    private fun initMarquee() {
        val marqueeViewListOf = mutableListOf<String>()
        marqueeViewListOf.add("夏日炎炎")
        marqueeViewListOf.add("第一波福利还有30秒到达战场")
        marqueeViewListOf.add("新用户立领1000元优惠卷")
        val marqueeAdapter = ShopAdapter(context, marqueeViewListOf)
        marquee_item.setAdapter(marqueeAdapter)
    }

    //TODO 点击监听
    private fun initListener() {
        tv_home_brand_name.setOnClickListener(this)
        tv_home_newGoods_name.setOnClickListener(this)
    }

    //是一种解耦，通过观察者监听 在Homeviewmodel获得数据
    private fun initVm() {
        //初始化ViewModel
        // :: 表示把一个方法当做一个参数，传递到另一个方法中进行使用，通俗的来讲就是引用一个方法
        homeViewVM = ViewModelProvider(this).get(HomeViewModel::class.java)

        //监听网络状态的变化  使用观察者监听
        homeViewVM.loadStatue.observe(this, Observer { status ->
            if (status == -1) {
                Log.i("TAG", "数据加载失败")
            }
        })

        //监听轮播图数据的变化
        homeViewVM.banner.observe(this, Observer { banner ->
            Log.i("TAG", "banner")
            mBanner_home!!.setImages(banner).setImageLoader(object : ImageLoader() {
                override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                    //todo   转换path类型
                    var pic = path as Banner
                    Glide.with(context!!).load(pic.image_url).into(imageView!!)
                }
            }).start()//开启Banner
        })

        //监听动态栏数据的变化  对viewmodel通过反射获得实例
        homeViewVM.channel.observe(this, Observer { channel ->
            // 清除所有布局
            mLl_home_channel.removeAllViews()
            // 权重
            val layoutParams =
                LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f)
            //循环  in修饰  indices 长度
            for (i in channel.indices) {
                val inflate = LayoutInflater.from(activity).inflate(R.layout.layout_channel_item, null)

                inflate.tv_channel_name.text = channel.get(i).name
                Glide.with(this).load(channel.get(i).icon_url).into(inflate.iv_channel_img)
                inflate.tv_channel_name.gravity = Gravity.CENTER      // 文字居中
                inflate.layoutParams = layoutParams       //  占位
                mLl_home_channel.addView(inflate)             // 添加布局

                //用于点击事件
                inflate.setTag(channel)
                //点击跳转
                inflate.setOnClickListener {
                    //取值
                    var name = channel!!.get(i).name
                    var url = channel!!.get(i).url
                    //传值
                    SpUtils.instance!!.remove("home_url")
                    SpUtils.instance!!.setValue("home_url", url)
                    val intent = Intent(activity, ChannelActivity::class.java)
                    intent.putExtra("name", name)
                    startActivity(intent)
                }
            }

        })

        //监听品牌直供数据的变化
        homeViewVM.brand.observe(this, Observer { brandList ->
            //布局管理器
            mRlv_home_brand.layoutManager = GridLayoutManager(activity, 2)
            var brandAdapter = BrandAdapter(brandList, context)
            //绑定适配器
            mRlv_home_brand.adapter = brandAdapter

            //条目点击
            brandAdapter!!.setOnItemClick(object : MyItemClick {
                override fun onItemCilck(pos: Int) {
                    val intent = Intent(activity, BrandActivity::class.java)
                    var id = brandList.get(pos)!!.id
                    startActivity(intent)
                }
            })
        })

        //监听新品首发数据的变化
        homeViewVM.newGoods.observe(this, Observer { newGoodsList ->
            //布局管理器
            mRlv_home_newGoods.layoutManager = GridLayoutManager(activity, 2)
            var newGoodsAdapter = NewGoodsAdapter(newGoodsList, context)
            //绑定适配器
            mRlv_home_newGoods.adapter = newGoodsAdapter

            //条目点击
            newGoodsAdapter!!.setOnItemClick(object :MyItemClick{
                override fun onItemCilck(pos: Int) {
                    val intent = Intent(activity, CategoryActivity::class.java)
                    var id = newGoodsList.get(pos)!!.id
                    SpUtils.instance!!.remove("Category_id")
                    SpUtils.instance!!.setValue("Category_id", id)
                    startActivity(intent)
                }
            })

        })

        //监听人气推荐数据的变化
        homeViewVM.hotGoods.observe(this, Observer { hotGoodsList ->
            //布局管理器
            mRlv_home_hotGoods.layoutManager = LinearLayoutManager(activity)
            //分割线
            mRlv_home_hotGoods!!.addItemDecoration(
                DividerItemDecoration(
                    activity,
                    LinearLayout.VERTICAL
                )
            )
            var hotGoodsAdapter = HotGoodsAdapter(hotGoodsList, context)
            mRlv_home_hotGoods.adapter = hotGoodsAdapter

            //条目点击
            hotGoodsAdapter!!.setOnItemClick(object :MyItemClick{
                override fun onItemCilck(pos: Int) {
                    val intent = Intent(activity, CategoryActivity::class.java)
                    var id = hotGoodsList.get(pos)!!.id
                    SpUtils.instance!!.remove("Category_id")
                    SpUtils.instance!!.setValue("Category_id", id)
                    startActivity(intent)
                }
            })
        })

        //监听专题精选数据的变化
        homeViewVM.topic.observe(this, Observer { topicList ->
            //布局管理器
            mRlv_home_topic!!.layoutManager =
                LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            var topicAdapter = TopicAdapter(topicList, context)
            mRlv_home_topic.adapter = topicAdapter
        })

        //监听居家数据的变化
        homeViewVM.category.observe(this, Observer { categoryList ->
            //循环放入布局 布局嵌套布局
            for ((index, value) in categoryList.withIndex()) {
                var inflate = LayoutInflater.from(activity).inflate(R.layout.layout_home_item, null)
                inflate.txt_home_title.text = value.name

                var list = value.goodsList
                inflate.mRlv_home.layoutManager = GridLayoutManager(activity, 2)
                var categoryAdapter = CategoryAdapter(list, context)
                inflate.mRlv_home.adapter = categoryAdapter

                mLl_category.addView(inflate)

                //条目点击
                categoryAdapter!!.setOnItemClick(object :MyItemClick{
                    override fun onItemCilck(pos: Int) {
                        val intent = Intent(activity, CategoryActivity::class.java)
                        var id = list.get(pos)!!.id
                        SpUtils.instance!!.remove("Category_id")
                        SpUtils.instance!!.setValue("Category_id", id)
                        startActivity(intent)
                    }
                })
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_home_brand_name -> {
                val intent = Intent(
                    context,
                    BrandNameActivity::class.java
                )
                startActivity(intent)
            }

            R.id.tv_home_newGoods_name -> {
                val intent = Intent(
                    context,
                    NewGoodsActivity::class.java
                )
                startActivity(intent)
            }
        }
    }

}