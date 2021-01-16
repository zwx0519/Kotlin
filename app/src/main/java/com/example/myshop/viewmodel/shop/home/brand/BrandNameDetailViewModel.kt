package com.example.myshop.viewmodel.shop.home.brand

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.model.bean.shop.home.brand.Brand
import com.example.myshop.model.bean.shop.home.brand.DataX
import com.example.myshop.model.bean.shop.home.brand.FilterCategory
import com.example.myshop.model.bean.shop.home.brand.ListData
import com.shop.base.BaseViewModel
import com.shop.net.Injection
import kotlinx.coroutines.launch

class BrandNameDetailViewModel : BaseViewModel(Injection.repository){
    var brandname : MutableLiveData<Brand> = MutableLiveData()
    var brandnameList : MutableLiveData<List<ListData>> = MutableLiveData()

    fun getBrandNameDetail(id:Int) {
        viewModelScope.launch {
            var result = repository.getBrandNameDetail(id)
            if(result.errno == 0){
                brandname.postValue(result.data.brand)
            }
        }
    }

    fun getBrandNameDetailList(id:Int) {
        viewModelScope.launch {
            var result = repository.getBrandNameDetailList(id)
            if(result.errno == 0){
                brandnameList.postValue(result.data.data)
            }
        }
    }

}