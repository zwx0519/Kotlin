package com.example.myshop.ui.shop

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.myshop.R
import com.example.myshop.adapter.shop.ShopAdapter
import com.example.myshop.databinding.ActivityShopBinding
import com.example.myshop.viewmodel.shop.ShopViewModel
import com.shop.base.BaseActivity
import kotlinx.android.synthetic.main.activity_shop.*

class ShopActivity : BaseActivity<ShopViewModel,ActivityShopBinding>
    (R.layout.activity_shop, ShopViewModel::class.java) {

    var pagerAdapter:ViewPage? = null

    override fun initView() {
        //设置适配器
        pagerAdapter = ViewPage(supportFragmentManager,mViewModel.fragments)
        //绑定适配器
        mVp_shop.adapter = pagerAdapter
        //设置ViewPager监听
        mVp_shop.addOnPageChangeListener(MyPagerChangeListener())
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        //BottomNavigationViewHelper.disableShiftMode(mbnv_shop)

        //设置底部导航的监听
        mbnv_shop.setOnNavigationItemSelectedListener {item-> //默认 it
            when(item.itemId){
                R.id.navigation_home -> {//点击首页 更换图标
                    item.setIcon(R.mipmap.ic_menu_choice_pressed)
                    mVp_shop.currentItem = 0 //ViewPager切换条目为0
                }
                R.id.navigation_special  -> {//点击专题 更换图标
                    item.setIcon(R.mipmap.ic_menu_topic_pressed)
                    mVp_shop.currentItem = 1 //ViewPager切换条目为1
                }
                R.id.navigation_type-> {//点击分类 更换图标
                    item.setIcon(R.mipmap.ic_menu_sort_pressed)
                    mVp_shop.currentItem = 2//ViewPager切换条目为2
                }
                R.id.navigation_shopping -> {//点击购物车 更换图标
                    item.setIcon(R.mipmap.ic_menu_shoping_pressed)
                    mVp_shop.currentItem = 3//ViewPager切换条目为3
                }
                R.id.navigation_me -> {//点击我的 更换图标
                    item.setIcon(R.mipmap.ic_menu_me_pressed)
                    mVp_shop.currentItem = 4//ViewPager切换条目为4
                }
            }

            return@setOnNavigationItemSelectedListener false
        }

    }

    override fun initVM() {

    }

    override fun initData() {

    }

    override fun initVariable() {

    }

    //TODO innner修饰的内部类访问外部类
    inner class MyPagerChangeListener: ViewPager.OnPageChangeListener{
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            //点击哪里 滑动到哪里
            mbnv_shop.getMenu().getItem(position).setChecked(true)
        }

        override fun onPageScrollStateChanged(state: Int) {
        }

    }

    //TODO  viewpager适配器
    class ViewPage(
        supportFragmentManager: FragmentManager, val list: List<Fragment>): FragmentStatePagerAdapter(supportFragmentManager) {
        override fun getCount(): Int {
            return list.size
        }

        override fun getItem(position: Int): Fragment {
            return list.get(position)
        }

    }
}