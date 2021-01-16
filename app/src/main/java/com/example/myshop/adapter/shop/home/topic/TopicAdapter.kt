package com.example.myshop.adapter.shop.home.topic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshop.R
import com.example.myshop.model.bean.shop.home.Topic
import kotlinx.android.synthetic.main.layout_topic_item.view.*

class TopicAdapter(private val list: List<Topic>, private val mContext: Context?) :
    RecyclerView.Adapter<TopicAdapter.ViewHolder>(){
    class ViewHolder(item: View) :RecyclerView.ViewHolder(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_topic_item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //TODO  赋值
        with(holder?.itemView!!) {
            val bean = list.get(position)
            tv_topic_name.text = bean.title
            tv_topic_price.text = "￥" + bean.price_info+"元起"
            tv_topic_subtitle.text=bean.subtitle
            if (mContext != null) {
                Glide.with(mContext).load(bean.item_pic_url).into(iv_topic_img)
            }
        }
    }

}