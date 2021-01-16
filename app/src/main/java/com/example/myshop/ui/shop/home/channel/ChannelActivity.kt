package com.example.myshop.ui.shop.home.channel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myshop.R
import com.example.myshop.viewmodel.shop.home.channel.ChannelViewModel
import kotlinx.android.synthetic.main.activity_channel.*

class ChannelActivity : AppCompatActivity() {

    var name:String? = null
    lateinit var homeVM: ChannelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)

        initVM()
        showCategory()
        homeVM.loadChanneltypeData()
    }

    //显示数据
    private fun showCategory(){
        //禁止滑动
        mVp_channel!!.setScanScroll(false)

        //获取的name
        name = intent.getStringExtra("name")

    }

    fun initVM(){
        //初始化ViewModel
        homeVM = ViewModelProvider(this).get(ChannelViewModel::class.java)
        //监听网络状态的变化
        homeVM.loadStatue.observe(this, Observer { stauts ->
            if (stauts == -1) {
                Log.e("TAG", "HomeChannelTypeActivity: "+"数据加载失败" )
            }
        })

        //监听数据的变化
        homeVM.category.observe(this, Observer { category ->
            val fs = ArrayList<ChannelTypeFragment>()
            for (i in category.indices) {
                val id = category[i].id
                val name1 = category[i].name
                val frontName = category[i].front_name

                var f = ChannelTypeFragment()

                val bundle = Bundle()
                bundle.putInt("channel_id",id)
                bundle.putString("channel_name", name1)
                bundle.putString("front_name", frontName)

                f.arguments = bundle
                fs.add(f)

            }

            //创建适配器
            mVp_channel!!.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
                override fun getCount(): Int {
                    return fs.size
                }

                override fun getItem(position: Int): Fragment {
                    return fs[position]
                }

                override fun getPageTitle(position: Int): CharSequence? {
                    return category[position].name
                }
            }
            mTab_channel!!.setupWithViewPager(mVp_channel)

            for (i in category.indices){
                //如果获取的name == 集合的name
                if (name == category[i].name) {
                    //被选中   select
                    mTab_channel!!.getTabAt(i)!!.select()
                }
            }

        })
    }
}