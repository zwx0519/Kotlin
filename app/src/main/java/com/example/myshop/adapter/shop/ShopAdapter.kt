package com.example.myshop.adapter.shop

import android.content.Context
import android.widget.TextView
import com.example.myshop.R
import com.xj.marqueeview.base.CommonAdapter
import com.xj.marqueeview.base.ViewHolder

class ShopAdapter(
    context: Context?,
    data: List<String?>?) :
    CommonAdapter<String?>(context, R.layout.layout_shop_item, data) {

    override fun convert(viewHolder: ViewHolder?, item: String?, position: Int) {
        val view: TextView = viewHolder!!.getView(R.id.tv_simple_text)
        view.text = item
    }

}