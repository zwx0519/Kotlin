package com.example.myshop.base

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

//TODO 1.上下文 2.集合 3.布局（也可能是多布局 所以使用轻量级集合） 4.点击事件 （多个点击）

open abstract class BaseAdapter<D>
    (val context: Context, var list:List<D>,
     val layouts:SparseArray<Int>,
     var itemClick:IItemClick<D>):RecyclerView.Adapter<BaseAdapter<D>.BaseVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        return BaseVH(DataBindingUtil.inflate(LayoutInflater.from(parent.context),viewType,parent,false))
    }

    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        var layoutId = getItemViewType(position)
        var type = layouts.get(layoutId)

        //界面组件显示数据的绑定
        holder.dataBinding.setVariable(type,list.get(position))
        holder.dataBinding.root.tag = list.get(position)

        //item的动态点击事件
        holder.dataBinding.root.setOnClickListener {
            if(itemClick != null){
                itemClick.itemClick(list.get(position))
                bindIndex(position)
            }
        }
        bindData(holder.dataBinding,list.get(position),layoutId)
    }

    //TODO 集合长度
    override fun getItemCount(): Int {
        return list.size
    }

    //TODO 多布局
    override fun getItemViewType(position: Int): Int {
        return layoutId(position)
    }

    //TODO 获取对应的布局（多布局）
    protected abstract fun layoutId(position: Int): Int

    //TODO 获取对应的下标
    protected  abstract fun bindIndex(position: Int)

    //TODO 视图和集合
    protected abstract fun bindData(binding: ViewDataBinding, data: D, layId: Int)

    //TODO 内联函数  内部类  参数是dataBinding 而不是View,因为dataBinding可以绑定数据，也可以显示页面
    inner class BaseVH(val dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(dataBinding.root)

    //等同上面
    //inner class BaseVH(view:View) :RecyclerView.ViewHolder(view)
}