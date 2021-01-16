package com.example.myshop.viewmodel.shop.home.newgoods

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.model.bean.shop.home.brand.ListData
import com.example.myshop.model.bean.shop.home.newgoods.*
import com.shop.base.BaseViewModel
import com.shop.net.Injection
import kotlinx.coroutines.launch
import java.util.HashMap

class NewGoodsViewModel : BaseViewModel(Injection.repository) {
    var newgoodsList: MutableLiveData<List<GoodsList>> = MutableLiveData()
    var newgoodsList1: MutableLiveData<List<FilterCategory>> = MutableLiveData()
    var newgoods: MutableLiveData<BannerInfo> = MutableLiveData()


    fun getNewGoodsLiat(map: HashMap<String, String>) {
        viewModelScope.launch {
            var result = repository.getNewGoodsList(map)
            if (result.errno == 0) {
                newgoodsList.postValue(result.data.goodsList)
                newgoodsList1.postValue(result.data.filterCategory)
            }
        }
    }

    fun getNewGoods() {
        viewModelScope.launch {
            var result = repository.getNewGoods()
            if (result.errno == 0) {
                newgoods.postValue(result.data.bannerInfo)
            }
        }
    }
}