package com.example.myshop.adapter.shop.shoppingcar

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.widget.CompoundButton
import androidx.databinding.ViewDataBinding
import com.example.myshop.R
import com.example.myshop.base.BaseAdapter
import com.example.myshop.base.IItemClick
import com.example.myshop.model.bean.shop.shoppingcar.ShoppingCarBean
import com.example.myshop.model.bean.shop.special.Data
import com.example.myshop.utils.NumberSelect
import kotlinx.android.synthetic.main.layout_shopping_item.view.*

class ShoppingCarAdapter(
    context: Context,
    list: List<ShoppingCarBean.Cart>,
    layouts: SparseArray<Int>,
    click: IItemClick<ShoppingCarBean.Cart>
) : BaseAdapter<ShoppingCarBean.Cart>(context, list, layouts, click) {

    //是否是编辑状态
    private var isEdit = false

    //是否修改条目
    private var updateItem: UpdateItem? = null

    fun setUpdateItem(item: UpdateItem?) {
        updateItem = item
    }

    private var iItemViewClick: IItemViewClick? = null

    //接口回调 给条目元素按钮点击
    fun addItemViewClick(click: IItemViewClick?) {
        iItemViewClick = click!!
    }

    override fun layoutId(position: Int): Int {
        return R.layout.layout_shopping_item

    }

    override fun bindIndex(position: Int) {

    }

    override fun bindData(binding: ViewDataBinding, data: ShoppingCarBean.Cart, layId: Int) {

        //进行显示隐藏
        binding.root.tv_shopping_car_item_name.setVisibility(if (isEdit) View.GONE else View.VISIBLE)
        binding.root.tv_shopping_car_item_count.setVisibility(if (isEdit) View.GONE else View.VISIBLE)
        binding.root.tv_shopping_car_item_title.setVisibility(if (isEdit) View.VISIBLE else View.GONE)
        binding.root.layout_change.setVisibility(if (isEdit) View.VISIBLE else View.GONE)

        val selectOrder = data.selectOrder
        val selectEdit = data.selectEdit
        // 设置选中状态
        binding.root.cb_shopping_car_select.setChecked(if (isEdit) selectEdit else selectOrder)

        binding.root.layout_change.addPage(R.layout.layout_number_change)
        binding.root.layout_change.addChangeNumber(object : NumberSelect.ChangeNumber {
            override fun change(number1: Int) {
                //修改本地数据得值
                data.copy(data.number, number1)
                updateItem?.updateItemDate(data)
            }
        })

        binding.root.cb_shopping_car_select.setTag(data.id)
//        binding.setVariable(BR.vmShopping_Cart_itemClick, click)

        binding.root.cb_shopping_car_select.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            @Override
            fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (iItemViewClick != null) {
                    val id = buttonView.tag as Int
                    iItemViewClick!!.itemViewClick(id, isChecked) //接口回调
                }
            }
        })

    }

    fun setEditState(bool: Boolean) {
        isEdit = bool
    }

    //修改条目
    interface UpdateItem {
        fun updateItemDate(data: ShoppingCarBean.Cart?)
    }

    //接口回调 给条目元素点击
    interface IItemViewClick {
        //条目中的元素点击
        fun itemViewClick(viewid: Int, data: Boolean)
    }

    /**
     * 加载完数据刷新到界面的data
     */
    fun refreshData(lt:List<ShoppingCarBean.Cart>){
        list = lt
        notifyDataSetChanged()
    }
}