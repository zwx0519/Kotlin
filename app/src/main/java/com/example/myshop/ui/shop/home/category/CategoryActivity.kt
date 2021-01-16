package com.example.myshop.ui.shop.home.category

import android.content.Context
import android.util.Log
import android.util.SparseArray
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.adapter.shop.home.category.CategoryAttributeAdapter
import com.example.myshop.adapter.shop.home.category.CategoryButtomInfoAdapter
import com.example.myshop.adapter.shop.home.category.CategoryIssueAdapter
import com.example.myshop.base.IItemClick
import com.example.myshop.databinding.ActivityCategoryBinding
import com.example.myshop.model.bean.shop.home.category.CategoryBean
import com.example.myshop.model.bean.shop.home.category.Goods
import com.example.myshop.viewmodel.shop.home.category.CategoryViewModel
import com.shop.base.BaseActivity
import com.shop.utils.SpUtils
import com.youth.banner.loader.ImageLoader

class CategoryActivity(var lid: Int = R.layout.activity_category) :
    BaseActivity<CategoryViewModel, ActivityCategoryBinding>(lid, CategoryViewModel::class.java) {

    //TODO 商品参数
    var categoryAttributeAdapter: CategoryAttributeAdapter? = null
    var categoryAttributeList: List<CategoryBean.Attribute> = arrayListOf()

    //TODO 常见问题
    var categoryIssueAdapter: CategoryIssueAdapter? = null
    var categoryIssueList: List<CategoryBean.Issue> = arrayListOf()

    //TODO 底部列表
    var categoryButtomInfoAdapter: CategoryButtomInfoAdapter? = null
    var categoryButtomInfoList: List<Goods> = arrayListOf()

    var id: Int? = null

    override fun initView() {

        val layoutManager = LinearLayoutManager(this)
        mDataBinding!!.mRlvCategoryIssue.layoutManager = layoutManager

        val layoutManager1 = LinearLayoutManager(this)
        mDataBinding!!.mRlvCategoryParameter.layoutManager = layoutManager1


        val layoutManager2 = GridLayoutManager(this, 2)
        mDataBinding!!.mRlvCategoryAll.layoutManager = layoutManager2

    }

    //TODO 获取数据
    override fun initVM() {
        id = SpUtils.instance!!.getInt("Category_id")
        Log.e("TAG", "id：" + id)

        if (id != null) {
            mViewModel.getCategory(id!!)
            mViewModel.getCategoryBottomInfo(id!!)
        } else {
            throw RuntimeException("ID不能为空")
        }

        initAttribute()
        initIssue()
        initButtomInfo()
    }

    override fun initData() {

    }

    override fun initVariable() {
        //数据
        mViewModel!!.getCategory.observe(this, Observer {
            categoryIssueAdapter!!.refreshData(it.get(0).issue)
            categoryAttributeAdapter!!.refreshData(it.get(0).attribute)
            //Banner轮播下的数据
            initInfo(it)
            //Banner轮播
            initBanner(it)

        })

        //底部列表数据
        mViewModel!!.getCategoryBottomInfo.observe(this, Observer {
            categoryButtomInfoAdapter!!.refreshData(it)
        })


    }
    //TODO Banner轮播数据
    private fun initBanner(it: List<CategoryBean>?) {
        mDataBinding.bannerCategory.setImages(it!!.get(0).gallery).setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context, path: Any, imageView: ImageView) {
                val bean: CategoryBean.Gallery = path as CategoryBean.Gallery
                Glide.with(context).load(bean.img_url).into(imageView)
            }
        }).start()
    }

    //TODO Banner轮播下的数据
    private fun initInfo(it: List<CategoryBean>) {
        mDataBinding.tvCategoryInfoTitle.setText(it.get(0).info.name)
        mDataBinding.tvCategoryInfoDesc.setText(it.get(0).info.goods_brief)
        mDataBinding.tvCategoryInfoPrice.setText("￥"+it.get(0).info.retail_price)
    }


    //TODO 底部列表数据
    private fun initButtomInfo() {
        //封装xml布局界面的id和界面中需要绑定的数据对应的id
        var array = SparseArray<Int>()
        array.put(R.layout.layout_category_item, BR.vmCategory_item)
        categoryButtomInfoAdapter =
            CategoryButtomInfoAdapter(this, categoryButtomInfoList, array, ButtonInfoItemClick())
        mDataBinding!!.mRlvCategoryAll.adapter = categoryButtomInfoAdapter
    }

    //TODO 常见问题数据
    private fun initIssue() {
        //封装xml布局界面的id和界面中需要绑定的数据对应的id
        var array = SparseArray<Int>()
        array.put(R.layout.layout_category_issue_item, BR.vmCategory_Issue)
        categoryIssueAdapter =
            CategoryIssueAdapter(this, categoryIssueList, array, IssueItemClick())
        mDataBinding!!.mRlvCategoryIssue.adapter = categoryIssueAdapter
    }

    //TODO 商品参数数据
    private fun initAttribute() {
        //封装xml布局界面的id和界面中需要绑定的数据对应的id
        var array = SparseArray<Int>()
        array.put(R.layout.layout_category_attribute_item, BR.vmCategory_Attribute)
        categoryAttributeAdapter =
            CategoryAttributeAdapter(this, categoryAttributeList, array, AttributeItemClick())
        mDataBinding!!.mRlvCategoryParameter.adapter = categoryAttributeAdapter
    }

    //TODO 底部列表内部类
    inner class ButtonInfoItemClick : IItemClick<Goods> {
        override fun itemClick(data: Goods?) {
            Log.i("TAG", data!!.name)
        }
    }

    //TODO 常见问题内部类
    inner class IssueItemClick : IItemClick<CategoryBean.Issue> {
        override fun itemClick(data: CategoryBean.Issue?) {
            Log.i("TAG", data!!.answer)
        }
    }

    //TODO 商品参数内部类
    inner class AttributeItemClick : IItemClick<CategoryBean.Attribute> {
        override fun itemClick(data: CategoryBean.Attribute?) {
            Log.i("TAG", data!!.name)
        }
    }
}