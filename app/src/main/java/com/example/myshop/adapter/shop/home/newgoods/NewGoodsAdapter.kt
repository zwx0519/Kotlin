package com.example.myshop.adapter.shop.home.newgoods

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshop.R
import com.example.myshop.model.bean.shop.home.NewGoods
import com.example.myshop.test.MyItemClick
import kotlinx.android.synthetic.main.layout_newgoods_item.view.*

class NewGoodsAdapter(private val list: List<NewGoods>, private val mContext: Context?) :
    RecyclerView.Adapter<NewGoodsAdapter.ViewHolder>() {
    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_newgoods_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO  赋值
        with(holder?.itemView!!) {
            val bean = list.get(position)
            tv_newgoods_name.text = bean.name
            tv_newgoods_retail_price.text = "￥" + bean.retail_price
            if (mContext != null) {
                Glide.with(mContext).load(bean.list_pic_url).into(iv_newgoods_img)
            }

            //设置接口
            holder.itemView.setOnClickListener{
                myItemClick!!.onItemCilck(position)
            }
        }
    }

    //定义条目回调接口
    private var myItemClick: MyItemClick? = null
    //set方法
    fun setOnItemClick(myItemClick: MyItemClick){
        this.myItemClick = myItemClick
    }
}