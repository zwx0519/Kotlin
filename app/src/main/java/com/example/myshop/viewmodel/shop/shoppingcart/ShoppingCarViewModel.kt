package com.example.myshop.viewmodel.shop.shoppingcart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.model.bean.shop.shoppingcar.ShoppingCarBean
import com.shop.base.BaseViewModel
import com.shop.net.Injection
import kotlinx.coroutines.launch

class ShoppingCarViewModel : BaseViewModel(Injection.repository){
    var ShoppingCar: MutableLiveData<List<ShoppingCarBean.Cart>> = MutableLiveData()

    //获取购物车的数据
    fun getSpecial(){
        //TODO 调用数据仓库需要协程产生一个联系
        viewModelScope.launch {
            var result = repository.getShoppingCar()
            if(result.errno == 0){
                //切换线程
                ShoppingCar.postValue(result.data.cartList)
            }else if(result.errno == 665){
                //刷新token
                refreshToken
            }
        }
    }
}