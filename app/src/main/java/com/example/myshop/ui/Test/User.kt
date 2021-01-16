package com.example.myshop.ui.Test

class User(var name:String,var age:Int){
    /**扩展函数**/
    fun User.Print(){
        print("用户名 $name 年龄：$age")
    }

    fun main(arg:Array<String>){
        var user = User("Runoob",18)
        user.Print()
    }
}