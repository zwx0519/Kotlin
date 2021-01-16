package com.example.myshop.viewmodel.shop.home.category

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.model.bean.shop.home.category.CategoryBean
import com.example.myshop.model.bean.shop.home.category.CategoryBottomInfoBean
import com.example.myshop.model.bean.shop.home.category.Goods
import com.example.myshop.model.bean.shop.home.newgoods.FilterCategory
import com.example.myshop.model.bean.shop.home.newgoods.GoodsList
import com.shop.base.BaseViewModel
import com.shop.net.Injection
import kotlinx.coroutines.launch


class CategoryViewModel : BaseViewModel(Injection.repository) {
    var getCategory: MutableLiveData<List<CategoryBean>> = MutableLiveData()
    var getCategoryBottomInfo: MutableLiveData<List<Goods>> = MutableLiveData()

    fun getCategory(id:Int) {
        viewModelScope.launch {
            var result = repository.getCategory(id)
            if (result.errno == 0) {
                getCategory.postValue(listOf(result.data))
            }
        }
    }

    fun getCategoryBottomInfo(id:Int) {
        viewModelScope.launch {
            var result = repository.getCategoryBottomInfo(id)
            if (result.errno == 0) {
                getCategoryBottomInfo.postValue(result.data.goodsList)
            }
        }
    }
}
