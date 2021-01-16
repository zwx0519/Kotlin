package com.example.myshop.viewmodel.shop.type

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.model.bean.shop.type.Category
import com.example.myshop.model.bean.shop.type.TypeInfoBean
import com.shop.base.BaseViewModel
import com.shop.net.Injection
import kotlinx.coroutines.launch

class TypeViewModel : BaseViewModel(Injection.repository){
    var gettype : MutableLiveData<List<Category>> = MutableLiveData()
    var gettype_info : MutableLiveData<List<TypeInfoBean.CurrentCategory>> = MutableLiveData()

    //获取分类
    fun getType(){
        //TODO 调用数据仓库需要协程产生一个联系
        viewModelScope.launch {
            var result = repository.getType()
            if(result.errno == 0){
                //切换线程
                gettype.postValue(result.data.categoryList)
            }else if(result.errno == 665){
                //刷新token
                refreshToken
            }
        }
    }

    //获取分类的数据
    fun getTypeInfo(id : Int){
        //TODO 调用数据仓库需要协程产生一个联系
        viewModelScope.launch {
            var result = repository.getTypeInfo(id)
            if(result.errno == 0){
                //切换线程
                gettype_info.postValue(listOf(result.data.currentCategory))
            }else if(result.errno == 665){
                //刷新token
                refreshToken
            }
        }
    }
}