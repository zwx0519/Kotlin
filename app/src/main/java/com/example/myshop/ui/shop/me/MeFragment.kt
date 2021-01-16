package com.example.myshop.ui.shop.me

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myshop.R

class MeFragment : Fragment() {
    //采用伴生对象 companion object==static 保证只加载一次
    companion object{
        val instance by lazy { MeFragment() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_me, container, false)
        return inflate
    }

}