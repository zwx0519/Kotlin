package com.example.myshop.ui.shop.shoppingcart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myshop.R
import com.example.myshop.ui.shop.me.MeFragment

class ShoppingCartFragment : Fragment() {
    //采用伴生对象 companion object==static
    companion object{
        //保证只加载一次
        val instance by lazy { ShoppingCartFragment() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false)
    }

}