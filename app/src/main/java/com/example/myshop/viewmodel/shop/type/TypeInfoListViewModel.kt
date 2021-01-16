package com.example.myshop.viewmodel.shop.type

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.model.bean.shop.type.DataX
import com.shop.base.BaseViewModel
import com.shop.net.Injection
import kotlinx.coroutines.launch

class TypeInfoListViewModel: BaseViewModel(Injection.repository) {
    //分类Vager列表数据12
    var getChannelTypeInfo: MutableLiveData<List<DataX>> = MutableLiveData()

    //分类Vager列表数据
    fun getChannelTypeInfo(id:Int){
        //TODO 调用数据仓库需要协程产生一个联系
        viewModelScope.launch {
            var result = repository.getChannelTypeInfo(id)
            if(result.errno == 0){
                getChannelTypeInfo.postValue(result.data.data)
            }
        }
    }
}