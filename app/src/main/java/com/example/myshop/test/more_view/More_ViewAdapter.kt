package com.example.myshop.test.more_view

import android.content.Context
import android.util.SparseArray
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.myshop.BR
import com.example.myshop.R
import com.example.myshop.base.BaseAdapter
import com.example.myshop.base.IItemClick
import com.example.myshop.model.bean.test.more_view.More_ViewBean
import com.example.myshop.model.bean.test.more_view.Stu

class More_ViewAdapter(
    context: Context,
    list: List<Stu>,
    layouts: SparseArray<Int>,
    var clicks: IItemClick<Stu>
) : BaseAdapter<Stu>(context, list, layouts, clicks) {

    override fun layoutId(position: Int): Int {
        var url = list.get(position).filePathList
        when (url.size) {
            0 -> return R.layout.layout_more_view_item_one
            1 -> return R.layout.layout_more_view_item_two
            else -> return R.layout.layout_more_view_item
        }
    }

    //刷新条目的数据
    override fun bindData(binding: ViewDataBinding, data: Stu, layId: Int) {
        var url =  data.filePathList
        when (layId) {
            R.layout.layout_more_view_item_two -> {
                //点击的名字
                binding.setVariable(BR.More_two, clicks)
                //获取界面的组件
               // Glide.with(context).load(url.get(0).filePath).into(binding.root.findViewById(R.id.iv_item_one_img_two))
            }
            R.layout.layout_more_view_item -> {
                binding.setVariable(BR.More_view, clicks)
               // Glide.with(context).load(url.get(0).filePath).apply(RequestOptions.bitmapTransform(RoundedCorners(10))).into(binding.root.findViewById(R.id.iv_item_one_img))
                //Glide.with(context).load(url.get(1).filePath).apply(RequestOptions.bitmapTransform(RoundedCorners(10))).into(binding.root.findViewById(R.id.iv_item_two_img))
                //Glide.with(context).load(url.get(2).filePath).apply(RequestOptions.bitmapTransform(RoundedCorners(10))).into(binding.root.findViewById(R.id.iv_item_three_img))
            }
        }

    }

    //刷新加载数据
    fun refreshData(lt : List<Stu>){
        list = lt
        notifyDataSetChanged()
    }
}