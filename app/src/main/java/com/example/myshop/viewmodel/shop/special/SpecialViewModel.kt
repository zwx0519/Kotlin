package com.example.myshop.viewmodel.shop.special

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.model.bean.shop.special.Data
import com.shop.base.BaseViewModel
import com.shop.net.Injection
import kotlinx.coroutines.launch

class SpecialViewModel : BaseViewModel(Injection.repository){
    var special: MutableLiveData<List<Data>> = MutableLiveData()

    //获取专题的数据
    fun getSpecial(page: Int){
        //TODO 调用数据仓库需要协程产生一个联系
        viewModelScope.launch {
            var result = repository.getSpecial(page)
            if(result.errno == 0){
                //切换线程
                special.postValue(result.data.data)
            }else if(result.errno == 665){
                //刷新token
                refreshToken
            }
        }
    }
}