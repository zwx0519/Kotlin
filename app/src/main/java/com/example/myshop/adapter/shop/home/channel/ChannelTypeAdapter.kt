package com.example.myshop.adapter.shop.home.channel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshop.R
import com.example.myshop.model.bean.shop.home.channel.ChannelTreeDataX
import com.example.myshop.test.MyItemClick
import kotlinx.android.synthetic.main.layout_channel_item_one.view.*

class ChannelTypeAdapter (private val datalist: List<ChannelTreeDataX>, private val mContext: Context?)
    : RecyclerView.Adapter<ChannelTypeAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelTypeAdapter.ViewHolder {
        val inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_channel_item_one, null)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return datalist?.size?:0
    }

    override fun onBindViewHolder(holder: ChannelTypeAdapter.ViewHolder, position: Int) {
        with(holder?.itemView!!){
            tv_channel1_name!!.text = datalist!!.get(position)!!.name
            tv_channel1_price!!.text = datalist!!.get(position)!!.retail_price+"元起"
            Glide.with(mContext!!).load(datalist!!.get(position)!!.list_pic_url).into(iv_channel1_img)
        }

        //设置接口
        holder.itemView.setOnClickListener{
            myItemClick!!.onItemCilck(position)
        }
    }

    //定义ViewHolder    名字 : 类型
    class ViewHolder(item: View):RecyclerView.ViewHolder(item)

    //定义条目回调接口
    private var myItemClick: MyItemClick? = null
    //set方法
    fun setOnItemClick(myItemClick: MyItemClick){
        this.myItemClick = myItemClick
    }
}