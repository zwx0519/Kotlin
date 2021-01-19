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

    //TODo 默认状态下的商品数据选中状态
    fun updateCarStateNormal(boolean: Boolean){
        var list = ShoppingCar.value!!
        for(i in 0 until list.size){
            list.get(i).selectOrder= boolean
        }
    }

    //TODO  编辑状态下的商品数据选中状态
    fun updateCarStateEidtor(boolean: Boolean){
        var list =  ShoppingCar.value!!
        for(i in 0 until list.size){
            list.get(i).selectEdit = boolean
        }
    }

    //TODO 计算当前购物车的总价和总数量
    fun getCarTotalNormal():IntArray{
        var arr:IntArray = intArrayOf(0,0,0)
        var num=0 //总数量
        var price = 0 //总价
        var select = 0 //0全选 1非全选
        var list = ShoppingCar.value!!
        for(i in 0 until list.size){
            if(list.get(i).selectOrder){
                num += list.get(i).number
                price += list.get(i).number * Integer.valueOf(list.get(i).retail_price)
            }else{
                if(select == 0){
                    select = 1
                }
            }
        }
        arr[0] = num
        arr[1] = price
        arr[2] = select
        return arr
    }

    //TODO 编辑状态下的计算
    fun getCarTotalEidt():Array<Int>{
        var arr = arrayOf<Int>()
        var num=0
        var price = 0
        var list = ShoppingCar.value!!
        for(i in 0 until list.size){
            if(list.get(i).selectOrder){
                num += list.get(i).number
                price += list.get(i).number*Integer.valueOf(list.get(i).retail_price)
            }
        }
        arr[0] = num
        arr[1] = price
        return arr
    }
}