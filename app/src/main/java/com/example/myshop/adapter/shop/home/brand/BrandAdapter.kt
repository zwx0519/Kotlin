package com.example.myshop.adapter.shop.home.brand

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshop.R
import com.example.myshop.test.MyItemClick
import com.example.myshop.model.bean.shop.home.Brand
import kotlinx.android.synthetic.main.layout_brand_item.view.*
//参数   继承RecyclerView.Adapter
//类名后面加了一个? 意思就是这个类可以为空 即可以 = null
class BrandAdapter(private val list: List<Brand?>?, private val mContext: Context?) :
    RecyclerView.Adapter<BrandAdapter.ViewHolder>(){

    //定义ViewHolder
    class ViewHolder(item: View) :RecyclerView.ViewHolder(item)

    //找布局
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_brand_item, null)
        return ViewHolder(view)
    }

    // ?: 左侧表达式非空，elvis操作符就返回其左侧表达式，否则返回右侧表达式
    //请注意，当且仅当左侧为空时，才会对右侧表达式求值。
    //对象A ?: 对象B 表达式，意思为，当对象 A值为 null 时，那么它就会返回后面的对象 B。

    // ?.意思是这个参数可以为空,并且程序继续运行下去
    override fun getItemCount(): Int {
        return list?.size?:0
    }

    //  !!. 的意思是这个参数如果为空,就抛出异常
    // !!加在变量名后，表示当前对象不为空的情况下执行 放在对象后面代表该对象如果为null则抛异常
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder ?.itemView!!){
            val bean = list!!.get(position)
            tv_brand_name.text=bean!!.name
            tv_brand_floor_price.text=bean!!.floor_price+"元起"
            if (mContext != null) {
                Glide.with(mContext).load(bean!!.new_pic_url).into(iv_brand_img)
            }
        }

        //设置接口
        holder.itemView.setOnClickListener{
            myItemClick!!.onItemCilck(position)
        }
    }



    //定义条目回调接口
    private var myItemClick: MyItemClick? = null
    //set方法
    fun setOnItemClick(myItemClick: MyItemClick){
        this.myItemClick = myItemClick
    }


}