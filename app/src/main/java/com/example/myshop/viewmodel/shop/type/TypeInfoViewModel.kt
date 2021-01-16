package com.example.myshop.viewmodel.shop.type

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.model.bean.shop.home.channel.ChannelTreeGoods
import com.example.myshop.model.bean.shop.type.TypeInfoTopBean
import com.shop.base.BaseViewModel
import com.shop.net.Injection
import kotlinx.coroutines.launch

class TypeInfoViewModel : BaseViewModel(Injection.repository){
    var getChannelType : MutableLiveData<List<TypeInfoTopBean.BrotherCategory>> = MutableLiveData()

    //获取分类
    fun getChannelType(categoryId:Int){
        //TODO 调用数据仓库需要协程产生一个联系
        viewModelScope.launch {
            var result = repository.getChannelType(categoryId)
            Log.e("TAG",result.toString())
            if(result.errno == 0){
                //切换线程
                getChannelType.postValue(result.data.brotherCategory)
            }else if(result.errno == 665){
                //刷新token
                refreshToken
            }
        }
    }

}