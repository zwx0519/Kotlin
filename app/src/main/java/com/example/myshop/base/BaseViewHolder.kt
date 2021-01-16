package com.example.myshop.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder(var dataBinding:ViewDataBinding):RecyclerView.ViewHolder(dataBinding.root) {

}