package com.example.myshop.ui.shop.home.newgoods

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.adapter.shop.home.newgoods.NewGoodsListAdapter
import com.example.myshop.adapter.shop.home.newgoods.NewGoodsPopuAdapter
import com.example.myshop.base.IItemClick
import com.example.myshop.databinding.ActivityNewGoodsBinding
import com.example.myshop.model.bean.shop.home.newgoods.FilterCategory
import com.example.myshop.model.bean.shop.home.newgoods.GoodsList
import com.example.myshop.ui.shop.home.brand.BrandNameDetailActivity
import com.example.myshop.ui.shop.home.category.CategoryActivity
import com.example.myshop.viewmodel.shop.home.newgoods.NewGoodsViewModel
import com.shop.base.BaseActivity
import com.shop.utils.SpUtils
import kotlinx.android.synthetic.main.activity_new_goods.*

class NewGoodsActivity(var lid: Int = R.layout.activity_new_goods) :
    BaseActivity<NewGoodsViewModel, ActivityNewGoodsBinding>(lid, NewGoodsViewModel::class.java),
    View.OnClickListener {
    var list: MutableList<GoodsList> = mutableListOf()
    var populist: MutableList<FilterCategory> = mutableListOf()
    lateinit var newgoods: NewGoodsListAdapter//集合适配器
    lateinit var newFirstTabAdapter: NewGoodsPopuAdapter//弹窗的适配器

    private var popupWindow: PopupWindow? = null

    private val ASC = "asc" //升序
    private val DESC = "desc" //降序
    private val DEFAULT = "default" //默认
    private val PRICE = "price" //价格
    private val CATEGORY = "category" //类别

    //是否是新品
    private val isNew = 1 //isNew=1 是否为新品
    private val page = 1 // &page=1
    private val size: String = "1000"     // &size =1000
    private var order: String = "asc"     // &order=asc
    private var sort: String = "default" // &sort=default
    private var categoryId = 0// &categoryId=0
    var context: Context? = null

    override fun initView() {
        context = this
        mDataBinding.mRlvNewGoodList.layoutManager = GridLayoutManager(this, 2)
        var goodListArr = SparseArray<Int>()
        goodListArr.put(R.layout.layout_newgoods_item, BR.vmNewGoodsList);
        //设置适配器
        newgoods = NewGoodsListAdapter(this, list, goodListArr, itemClick())
        mDataBinding.mRlvNewGoodList.adapter = newgoods

        order = ASC
        sort = DEFAULT
        categoryId = 0
        mDataBinding.layoutPrice.setTag(0)

        tv_newgoods_list_all.setTextColor(Color.parseColor("#ff0000"))
        mDataBinding.setVariable(R.layout.activity_new_goods, BR.vmNewGoods)

        //点击监听
        initListener()
    }

    //TODO 点击监听
    fun initListener() {
        tv_newgoods_list_price!!.setOnClickListener(this)
        tv_newgoods_list_sort!!.setOnClickListener(this)
        tv_newgoods_list_all!!.setOnClickListener(this)
    }

    //TODO 刷新适配器的数据
    override fun initVM() {
        mViewModel.newgoodsList.observe(this, Observer {
            newgoods!!.refreshData(it)
        })

        mViewModel.newgoods.observe(this, Observer {
            mDataBinding.setVariable(BR.vmNewGoods, it)
            Glide.with(this).load(it.img_url).into(iv_newgoods_list_img)
        })

        //刷新适配器
        mViewModel.newgoodsList1.observe(this, Observer {
            if (sort == CATEGORY) {
                sort = DEFAULT
                setPopw(it) //弹窗
            }

        })
    }

    override fun initData() {
        //商品列表数据
        var map = HashMap<String, String>()
        mViewModel.getNewGoodsLiat(map)
        //商品详情上面数据
        mViewModel.getNewGoods()
    }

    override fun initVariable() {

    }

    //TODO 组装当前的接口参数
    private fun getParam(): HashMap<String, String>? {
        val map = HashMap<String, String>()
        map["isNew"] = isNew.toString()
        map["page"] = page.toString()
        map["size"] = size.toString()
        map["order"] = order!!
        map["sort"] = sort!!
        map["category"] = categoryId.toString()
        return map
    }

    //TODO 按价格升序排序
    @SuppressLint("ResourceType")
    private fun priceStateUp() {
        mDataBinding.imgArrowUp.setImageResource(R.mipmap.ic_arrow_up_select)
        mDataBinding.imgArrowDown.setImageResource(R.mipmap.ic_arrow_down_normal)
        mDataBinding.tvNewgoodsListPrice.setTextColor(Color.parseColor(getString(R.color.red)))
    }

    //TODO 价格的降序排列
    @SuppressLint("ResourceType")
    private fun priceStateDown() {
        mDataBinding.imgArrowUp.setImageResource(R.mipmap.ic_arrow_up_normal)
        mDataBinding.imgArrowDown.setImageResource(R.mipmap.ic_arrow_down_select)
        mDataBinding.tvNewgoodsListPrice.setTextColor(Color.parseColor(getString(R.color.red)))
    }

    //TODO 重置条件选择的所有状态
    @SuppressLint("ResourceType")
    private fun resetPriceState() {
        mDataBinding.imgArrowUp.setImageResource(R.mipmap.ic_arrow_up_normal)
        mDataBinding.imgArrowDown.setImageResource(R.mipmap.ic_arrow_down_normal)
        mDataBinding.tvNewgoodsListPrice.setTextColor(Color.parseColor(getString(R.color.black)))
        mDataBinding.tvNewgoodsListAll.setTextColor(Color.parseColor(getString(R.color.black)))
        mDataBinding.tvNewgoodsListSort.setTextColor(Color.parseColor(getString(R.color.black)))
        mDataBinding.layoutPrice.setTag(0)
    }

    inner class itemClick : IItemClick<GoodsList> {
        override fun itemClick(data: GoodsList?) {
            //清空
            SpUtils.instance!!.remove("Category_id")
            SpUtils.instance!!.setValue("Category_id", data!!.id)
            val intent = Intent(context, CategoryActivity::class.java)
            startActivity(intent)
        }
    }

    //TODO 界面的点击事件
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_newgoods_list_price -> {//点击价格
                val tag = mDataBinding.layoutPrice.getTag() as Int
                if (tag == 0) {
                    resetPriceState()//重置条件选择的所有状态
                    priceStateUp()
                    mDataBinding.layoutPrice.setTag(1)
                    order = ASC
                } else if (tag == 1) {
                    resetPriceState()//重置条件选择的所有状态
                    priceStateDown()
                    mDataBinding.layoutPrice.setTag(0)
                    order = DESC
                }
                sort = PRICE
                getParam()?.let { mViewModel.getNewGoodsLiat(it) }
            }
            R.id.tv_newgoods_list_all -> {//点击全部
                resetPriceState()//重置条件选择的所有状态
                tv_newgoods_list_all.setTextColor(Color.parseColor("#ff0000"))
                sort = DEFAULT
                categoryId = 0
                getParam()?.let { mViewModel.getNewGoodsLiat(it) }
            }
            R.id.tv_newgoods_list_sort -> {//点击分类
                resetPriceState()//重置条件选择的所有状态
                tv_newgoods_list_sort.setTextColor(Color.parseColor("#ff0000"))
                sort = CATEGORY
                getParam()?.let { mViewModel.getNewGoodsLiat(it) }
            }
        }
    }

    //TODO 弹窗
    private fun setPopw(it: List<FilterCategory>) {
        //PopWindow条目布局
        val popupView: View =
            LayoutInflater.from(this@NewGoodsActivity).inflate(R.layout.layout_newgoods_popw, null)
        //设置popu
        popupWindow =
            PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        //找到视图
        popupWindow!!.contentView = popupView
        popupWindow!!.isClippingEnabled = false
        //控制点击pw范围以外的空间关闭pw  设置Pw以外的空间可以点击
        popupWindow!!.setOutsideTouchable(true)
        //设置背景  告知pw的范围
        popupWindow!!.setBackgroundDrawable(null)
        popupWindow!!.isFocusable = true

        val mRlv_popu: RecyclerView = popupView.findViewById(R.id.rlv_newgoods_popu)
        mRlv_popu.layoutManager = GridLayoutManager(this, 5)

        var goodListArr = SparseArray<Int>()
        goodListArr.put(R.layout.layout_newgoods_popw_item, BR.vmNewgoods_popu);
        //设置适配器
        newFirstTabAdapter = NewGoodsPopuAdapter(this@NewGoodsActivity, populist, goodListArr, BtnClick())
        //绑定适配器
        mRlv_popu.adapter = newFirstTabAdapter

        //刷新适配器
        newFirstTabAdapter!!.refreshData(it)

        //在按钮的下方弹出  无偏移 第一种方式
        popupWindow!!.showAsDropDown(mDataBinding.layoutSort) //开启弹窗
    }

    //点击事件
    inner class BtnClick : IItemClick<FilterCategory> {
        override fun itemClick(data: FilterCategory?) {
            val stringStringHashMap = HashMap<String, String>()
            stringStringHashMap["categoryId"] = "" + data!!.id
            stringStringHashMap["isNew"] = "" + 1
            //调用方法
            mViewModel.getNewGoodsLiat(stringStringHashMap)
            popupWindow!!.dismiss() //关闭popupWindow
        }
    }


}