package com.example.myshop.adapter.shop.shoppingcar

import android.content.Context
import android.util.SparseArray
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.base.BaseAdapter
import com.example.myshop.base.IItemClick
import com.example.myshop.ext.findView
import com.example.myshop.model.bean.shop.shoppingcar.ShoppingCarBean
import com.example.myshop.model.bean.shop.special.Data
import com.example.myshop.utils.NumberSelect
import kotlinx.android.synthetic.main.layout_shopping_item.view.*

class ShoppingCarAdapter(
    context: Context,
    list: List<ShoppingCarBean.Cart>,
    layouts: SparseArray<Int>,
   var click: IItemClick<ShoppingCarBean.Cart>
) : BaseAdapter<ShoppingCarBean.Cart>(context, list, layouts, click) {

    //是否是编辑状态
    var isEditor:Boolean = false

    //是否修改条目
    private var updateItem: UpdateItem? = null
    fun setUpdateItem(item: UpdateItem?) {
        updateItem = item
    }

    //条目监听
    private lateinit var changeEvt:ChangeEvt
    fun addChangeEvt(changeEvt: ChangeEvt){
        this.changeEvt = changeEvt
    }

    override fun layoutId(position: Int): Int {
        return R.layout.layout_shopping_item

    }

    override fun bindIndex(position: Int) {

    }

    override fun bindData(binding: ViewDataBinding, data: ShoppingCarBean.Cart, layId: Int) {

        var checkbox = binding.root.findViewById<CheckBox>(R.id.cb_shopping_car_select)

        var txtName = binding.root.findViewById<TextView>(R.id.tv_shopping_car_item_name)
        var txtNumber = binding.root.findViewById<TextView>(R.id.tv_shopping_car_item_count)

        var txtEditTitle = binding.root.findViewById<TextView>(R.id.tv_shopping_car_item_price)
        var numberSelect = binding.root.findViewById<NumberSelect>(R.id.layout_change)

        //绑定界面的数据对象
        if(isEditor){
            txtName.visibility = View.GONE
            txtNumber.visibility = View.GONE
            txtEditTitle.visibility = View.VISIBLE
            numberSelect.visibility = View.VISIBLE

            checkbox.isChecked = data.selectEdit
            //设置自定义数量选择器的布局
            numberSelect.addPage(R.layout.layout_number_change)
            //设置数量变化的点击操作
            numberSelect.addChangeNumber {
                //修改后的商品的数量
                data.number= it
            }
        }else{
            txtName.visibility = View.VISIBLE
            txtNumber.visibility = View.VISIBLE
            txtEditTitle.visibility = View.GONE
            numberSelect.visibility = View.GONE
            checkbox.isChecked = data.selectOrder
        }
        txtNumber.setText("X"+data.number.toString())
        numberSelect.number = data.number

        checkbox.setOnClickListener {
            if(isEditor){
                data.selectEdit = checkbox.isChecked
            }else{
                data.selectOrder = checkbox.isChecked
            }
            if(changeEvt != null){
                changeEvt.click()
            }
        }

        //binding.setVariable(BR.vmShopping_Cart_itemClick, click)
    }

    //修改条目
    interface UpdateItem {
        fun updateItemDate(data: ShoppingCarBean.Cart?)
    }

    //点击事件
    interface ChangeEvt{
        fun click()
    }
}