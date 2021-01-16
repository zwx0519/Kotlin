package com.shop.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.app.Constants
import com.example.myshop.net.repository.SystemRepository
import com.example.myshop.utils.MyMmkv
import kotlinx.coroutines.launch

open class BaseViewModel(val repository: SystemRepository):ViewModel() {

    //TODO 定义一个网络请求状态的处理
    // MutableLiveData通过这个变量（带有声明周期） 可以把数据显示到界面
    protected var status:MutableLiveData<String> = MutableLiveData()

    //TODO  token刷新通知界面的数据状态
    var refreshToken:MutableLiveData<Int> = MutableLiveData()

    //TODO 刷新token
    protected fun refreshToken(){
        viewModelScope.launch {
            var result = repository.refreshToken()
            when(result.errno){
                0 -> {
                    MyMmkv.setValue(Constants.token,result.data)
                    refreshToken.postValue(1)
                }
                //如果失败 就刷新token
                665 -> {
                    refreshToken.postValue(2)
                }

            }
        }
    }

}