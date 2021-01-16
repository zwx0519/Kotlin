package com.example.myshop.app

class SingletonDemo private constructor() {
    //懒汉式单例
//    companion object{
//        private var instance :SingletonDemo?=null
//        get() {
//            if(field ==null){
//                field=SingletonDemo() //如果为空就创建对象
//            }
//            return field;
//        }
//        fun get() :SingletonDemo{
//            //这里不用getInstance作为为方法名，是因为在伴生对象声明时，内部已有getInstance方法，所以只能取其他名字
//            return instance!!
//        }
//    }

    //双重校验锁式
//    companion object {
//        val instance: SingletonDemo by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
//            SingletonDemo()
//        }
//    }


    /**
     * 写法一
     */

    private var mInstance: SingletonDemo? = null
    fun getInstance(): SingletonDemo {
        if (mInstance == null) {
            synchronized(SingletonDemo::class.java) {
                if (mInstance == null) {
                    mInstance == SingletonDemo()
                }
            }
        }
        return mInstance!!
    }

}