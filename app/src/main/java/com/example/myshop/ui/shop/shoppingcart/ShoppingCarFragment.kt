package com.example.myshop.ui.shop.shoppingcart

import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.adapter.shop.shoppingcar.ShoppingCarAdapter
import com.example.myshop.base.BaseFragment
import com.example.myshop.base.IItemClick
import com.example.myshop.databinding.FragmentShoppingCarBinding
import com.example.myshop.model.bean.shop.shoppingcar.ShoppingCarBean
import com.example.myshop.viewmodel.shop.shoppingcart.ShoppingCarViewModel

class ShoppingCarFragment : BaseFragment<ShoppingCarViewModel, FragmentShoppingCarBinding>
    (R.layout.fragment_shopping_car, ShoppingCarViewModel::class.java), View.OnClickListener {

    //采用伴生对象 companion object==static 保证只加载一次
    companion object {
        val instance by lazy { ShoppingCarFragment() }
    }

    lateinit var list: MutableList<ShoppingCarBean.Cart>

    private var isEdit = false//是否是编辑状态

    var shopping: ShoppingCarAdapter? = null

    //懒加载
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            list!!.clear()
            initData()
        }
    }

    override fun initView() {
        //initListener()
        list = mutableListOf()

        val layoutManager = LinearLayoutManager(activity)
        //设置布局管理器
        mDataBinding!!.mRlvShoppingCar.layoutManager = layoutManager

        //适配器
        var sortArr = SparseArray<Int>()
        sortArr.put(R.layout.layout_shopping_item, BR.vmShopping_Cart_item)
        shopping = ShoppingCarAdapter(context!!, list, sortArr, itemClick())
        mDataBinding!!.mRlvShoppingCar.adapter = shopping

        mDataBinding.setVariable(BR.Shopping_CartClick, ClickEvt())
        shopping!!.addChangeEvt(ItemChangeSelect())
    }

    private fun initListener() {
        mDataBinding.cbShoppingCarAll.setOnClickListener(this)
        mDataBinding.tvShoppingCarEdit.setOnClickListener(this)
        mDataBinding.tvShoppingCarSubmit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cb_Shopping_car_all -> {       //头像图片
                ClickEvt().clickAll(v)
            }
            R.id.tv_Shopping_Car_edit -> {       //名字
                ClickEvt().clickEdit(v)
            }
            R.id.tv_Shopping_Car_submit -> {       //签名
                ClickEvt().clickSubit(v)
            }
        }
    }

    //TODO 修改列表条目选择状态更新总价和数量
    inner class ItemChangeSelect : ShoppingCarAdapter.ChangeEvt {
        override fun click() {
            var arr = mViewModel.getCarTotalNormal()//计算当前购物车的总价和总数量
            //获得数组
            if (arr.size == 3) {
                mDataBinding.cbShoppingCarAll.setText("全选(" + arr[0] + ")")
                mDataBinding.tvShoppingCarTotalPrice.setText("￥" + arr[1])
                mDataBinding.cbShoppingCarAll.isChecked = if (arr[2] == 0) true else false
            }
        }
    }

    //TODO 获取数据
    override fun initVM() {
        mViewModel.ShoppingCar.observe(this, Observer {
            list.clear()
            list.addAll(it)
            shopping!!.notifyDataSetChanged()
        })
    }

    inner class itemClick : IItemClick<ShoppingCarBean.Cart> {
        override fun itemClick(data: ShoppingCarBean.Cart?) {
            Log.e("TAG", data!!.goods_name)
        }
    }

    //TODO 调用方法
    override fun initData() {
        mViewModel.getSpecial()//购物车列表
    }

    override fun initVariable() {

    }

    //TODO 当前界面的点击事件
    inner class ClickEvt {
        //条目上的选中状态的点击事件
        fun itemCheckboxClick(view: View) {
            if (view is CheckBox) {
                var checkbox = view as CheckBox
                var car: ShoppingCarBean.Cart = checkbox.tag as ShoppingCarBean.Cart
                if (isEdit) {
                    car.selectEdit = checkbox.isChecked
                } else {
                    car.selectOrder = checkbox.isChecked
                }
            }
        }

        //boolean true全选  false 非全选
        fun clickAll(view: View) {
            if (view is CheckBox) {
                var checkbox = view as CheckBox
                if (isEdit) {
                    mViewModel.updateCarStateEidtor(checkbox.isChecked)
                } else {
                    mViewModel.updateCarStateNormal(checkbox.isChecked)
                }

                //刷新总价和总数量
                var arr = mViewModel.getCarTotalNormal()
                if (arr.size == 2) {
                    mDataBinding.cbShoppingCarAll.setText("全选(" + arr[0] + ")")
                    mDataBinding.tvShoppingCarTotalPrice.setText("￥" + arr[1])
                }
                shopping!!.notifyDataSetChanged()
            }
        }

        //boolean true编辑 false完成
        fun clickEdit(view: View) {
            if (view is TextView) {
                var txt = view as TextView
                if (txt.text.toString() == "编辑") {
                    mDataBinding.tvShoppingCarEdit.setText("完成")
                    mDataBinding.tvShoppingCarSubmit.setText("删除所选")
                    isEdit = true
                } else {
                    mDataBinding.tvShoppingCarEdit.setText("编辑")
                    mDataBinding.tvShoppingCarSubmit.setText("下单")
                    isEdit = false
                }
                //控制界面在编辑和非编辑状态下的显示
                changeEditUI()
            }
        }

        //是否是提交
        fun clickSubit(view: View) {
            if (view is TextView) {
                var txt = view as TextView
                if (txt.text.toString() == "下单") {
                    //下单
                } else {
                    //删除对应的数据

                }
            }
        }
    }

    //TODO 控制界面在编辑和非编辑状态下的显示
    fun changeEditUI() {
        shopping!!.isEditor = isEdit
        shopping!!.notifyDataSetChanged()
    }
}

