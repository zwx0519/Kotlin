package com.example.myshop.viewmodel.shop.home.brand

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.model.bean.shop.home.brand.DataX
import com.shop.base.BaseViewModel
import com.shop.net.Injection
import kotlinx.coroutines.launch

class BrandNameViewModel : BaseViewModel(Injection.repository){

    var dataX : MutableLiveData<List<DataX>> = MutableLiveData(listOf())

    fun getBrandName(page:Int,size:Int) {
        viewModelScope.launch {
            var result = repository.getBrandName(page, size)
            if(result.errno == 0){
                dataX.postValue(result.data.data)
            }
        }

    }
}