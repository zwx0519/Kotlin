package com.example.myshop.test.more_view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.model.bean.shop.home.HomeBean
import com.example.myshop.model.bean.test.more_view.More_ViewBean
import com.example.myshop.model.bean.test.more_view.Stu
import com.google.gson.Gson
import com.shop.base.BaseViewModel
import com.shop.net.Injection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MoreViewModel  : BaseViewModel(Injection.repository){
    //采用MutableLiveData进行修饰 并进行实例化
    var stu : MutableLiveData<List<Stu>> = MutableLiveData(listOf())

    //TODO 获取数据
    fun getMore(){//协程
        viewModelScope.launch {
            var result = repository.getMore()
            if(result.status.statusCode == 100){
                stu.postValue(result.data.list)
            }
        }
    }
}
