package com.example.myshop.ui.shop.shoppingcart

import android.util.Log
import android.util.SparseArray
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.adapter.shop.shoppingcar.ShoppingCarAdapter
import com.example.myshop.base.BaseFragment
import com.example.myshop.base.IItemClick
import com.example.myshop.databinding.FragmentShoppingCartBinding
import com.example.myshop.model.bean.shop.shoppingcar.ShoppingCarBean
import com.example.myshop.viewmodel.shop.shoppingcart.ShoppingCarViewModel
import java.util.*

class ShoppingCarFragment : BaseFragment<ShoppingCarViewModel, FragmentShoppingCartBinding>
    (R.layout.fragment_shopping_cart, ShoppingCarViewModel::class.java), View.OnClickListener {

    companion object {
        //保证只加载一次
        val instance by lazy { ShoppingCarFragment() }
    }

    var list: MutableList<ShoppingCarBean.Cart> = mutableListOf()
    private val list1: ArrayList<ShoppingCarBean.Cart>? = null
    private var isEdit = false//是否是编辑状态
    var shopping: ShoppingCarAdapter? = null
    private var shoppingCarBean: ShoppingCarBean? = null

    //懒加载
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            list!!.clear()
            initData()
        }
    }

    override fun initView() {
        initListener()
        val layoutManager = LinearLayoutManager(activity)
        //设置布局管理器
        mDataBinding!!.mRlvShoppingCar.layoutManager = layoutManager
    }

    //TODO 监听事件
    private fun initListener() {
        mDataBinding.tvShoppingCarEdit.setOnClickListener(this)
        mDataBinding.tvShoppingCarSubmit.setOnClickListener(this)
        mDataBinding.cbShoppingCarAll.setOnClickListener(this)
    }

    //TODO 获取数据
    override fun initVM() {
        mViewModel.ShoppingCar.observe(this, Observer {
            list = it.toMutableList()
            initShopCar(list)
            shopping!!.refreshData(it)
        })
    }

    //TODO 进行绑定适配器
    private fun initShopCar(list: MutableList<ShoppingCarBean.Cart>) {
        var sortArr = SparseArray<Int>()
        sortArr.put(R.layout.layout_shopping_item, BR.vmShopping_Cart_item)
        shopping = ShoppingCarAdapter(context!!, list, sortArr, itemClick())
        mDataBinding!!.mRlvShoppingCar.adapter = shopping

        //监听条目元素点击的时候的接口回调
        shopping!!.addItemViewClick(object : ShoppingCarAdapter.IItemViewClick {
            override fun itemViewClick(viewid: Int, data: Boolean) {

                for (item in shoppingCarBean!!.cartList) {
                    if (item.id === viewid) {
                        if (!isEdit) {
                            item.selectOrder = data
                        } else {
                            item.selectEdit = data
                        }
                        break
                    }
                }
                val isSelectAll: Boolean
                if (!isEdit) {
                    isSelectAll = totalSelectOrder()
                } else {
                    isSelectAll = totalSelectEdit()
                }
                mDataBinding.cbShoppingCarAll.setChecked(isSelectAll)
            }
        })


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_Shopping_Car_edit -> changeEdit()
            R.id.tv_Shopping_Car_submit -> submit()
            R.id.cb_Shopping_car_all -> {
                val bool: Boolean = mDataBinding.cbShoppingCarAll.isChecked()
                if (isEdit) {
                    updateGoodSelectStateEdit(!bool)
                } else {
                    updateGoodSelectStateOrder(!bool)
                }
            }
        }
    }

    inner class itemClick : IItemClick<ShoppingCarBean.Cart> {
        override fun itemClick(data: ShoppingCarBean.Cart?) {
            Log.e("TAG", data!!.goods_name)
        }
    }

    override fun initData() {
        mViewModel.getSpecial()
    }

    override fun initVariable() {

    }


    //TODO 下单状态的数据刷新
    private fun updateGoodSelectStateOrder(isChecked: Boolean) {
        for (item in shoppingCarBean!!.cartList) {
            item.selectOrder = isChecked
        }
        totalSelectOrder()
        // 更新列表条目的选中状态
        shopping!!.refreshData(list)
    }

    //TODO 编辑状态下的数据刷新
    private fun updateGoodSelectStateEdit(isChecked: Boolean) {
        Log.e("111",shoppingCarBean!!.cartList.toString())
        for (item in shoppingCarBean!!.cartList) {
            item.selectEdit = isChecked
        }
        totalSelectEdit()
        // 更新列表条目的选中状态
        shopping!!.refreshData(list)
    }

    //TODO 下单状态下的总数和价格的计算
    private fun totalSelectOrder(): Boolean {
        var num = 0
        var totalPrice = 0
        var isSelectAll = true
        for (item in list!!) {
            if (item.selectOrder) {
                num += item.number
                totalPrice += item.number * Integer.valueOf(item.retail_price)
            } else {
                if (isSelectAll) {
                    isSelectAll = false
                }
            }
        }
        var strAll = "全选($)"
        strAll = strAll.replace("$", num.toString())
        mDataBinding.cbShoppingCarAll.setText(strAll)
        mDataBinding.tvShoppingCarTotalPrice.setText("￥" + totalPrice)
        return isSelectAll
    }

    //TODO 编辑状态下的总数和价格的计算
    private fun totalSelectEdit(): Boolean {
        var num = 0
        //int totalPrice = 0;
        var isSelectAll = true //全选
        for (item in list!!) {
            if (item.selectEdit) { //选中的数量
                num += item.number //数量
                //totalPrice += item.getNumber() * item.getRetail_price();//总价
            } else {
                if (isSelectAll) { //全选为false
                    isSelectAll = false
                }
            }
        }
        var strAll = "全选($)"
        strAll = strAll.replace("$", num.toString())
        mDataBinding.cbShoppingCarAll.setText(strAll)
        // tv_Price.setText("￥" + totalPrice);
        return isSelectAll
    }

    //TODO 修改编辑和完成的状态
    private fun changeEdit() {
        if ("编辑" == mDataBinding.tvShoppingCarEdit.getText().toString()) {
            mDataBinding.tvShoppingCarEdit.setText("完成")
            mDataBinding.tvShoppingCarSubmit.setText("删除所选")
            isEdit = true //是编辑状态
            //价格进行隐藏
            mDataBinding.tvShoppingCarTotalPrice.setVisibility(View.GONE)
            //updateGoodSelectStateOrder(false);
        } else if ("完成" ==  mDataBinding.tvShoppingCarEdit.getText().toString()) {
            mDataBinding.tvShoppingCarEdit.setText("编辑")
            mDataBinding.tvShoppingCarSubmit.setText("下单")
            isEdit = false //不是编辑状态
            updateGoodSelectStateEdit(false)
            //价格进行显示
            mDataBinding.tvShoppingCarTotalPrice.setVisibility(View.VISIBLE)
        }
        shopping!!.setEditState(isEdit) //删除

        shopping!!.refreshData(list)
    }


    //TODO 下单 提交
    private fun submit() {
        //list1!!.clear() //每次下单前清空一下集合
        if ("下单" == mDataBinding.tvShoppingCarSubmit.getText().toString()) {
            for (item in list!!) {
                if (item.selectEdit) {//在下单的状态勾选
                    //list1!!.add(item)
                }
            }
            //下单
            //  val intent = Intent(activity, OrderFormActivity::class.java)
            // MyApp.getMap().put("shoppinglist", list1)
            // startActivity(intent)
        } else if ("删除所选" == mDataBinding.tvShoppingCarSubmit.getText().toString()) {
            //删除购物车所选数据
            deleteCar()
        }
    }

    //TODO 删除所有选中的商品数据
    private fun deleteCar() {
        val sb = StringBuilder()
        for (item in list!!) {
            if (item.selectEdit) {
                sb.append(item.product_id)
                sb.append(",")
            }
        }
        if (sb.length > 0) {
            sb.deleteCharAt(sb.length - 1) //删除所选的数据
        }
        shopping!!.refreshData(list)
    }
}


