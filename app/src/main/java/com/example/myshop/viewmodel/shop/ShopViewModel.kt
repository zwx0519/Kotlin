package com.example.myshop.viewmodel.shop

import androidx.fragment.app.Fragment
import com.example.myshop.ui.shop.home.HomeFragment
import com.example.myshop.ui.shop.me.MeFragment
import com.example.myshop.ui.shop.shoppingcart.ShoppingCarFragment
import com.example.myshop.ui.shop.special.SpecialFragment
import com.example.myshop.ui.shop.type.TypeFragment
import com.shop.base.BaseViewModel
import com.shop.net.Injection

class ShopViewModel :BaseViewModel(Injection.repository){
    var fragments:MutableList<Fragment> = mutableListOf()

    //添加进入集合
    init {
        fragments.add(HomeFragment.instance)
        fragments.add(SpecialFragment.instance)
        fragments.add(TypeFragment.instance)
        fragments.add(ShoppingCarFragment.instance)
        fragments.add(MeFragment.instance)
    }
}