package com.shop.net

import android.util.Log
import com.example.myshop.app.Constants
import com.example.myshop.model.api.ServiceApi
import com.example.myshop.utils.MyMmkv
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory {

    /**
     * 伴生对象 单例
     */
    companion object{
        val instance:RetrofitFactory by lazy { RetrofitFactory() }
    }

    private val interceptor: Interceptor
    private val retrofit: Retrofit

    //初始化
    init {
        //通用拦截
        interceptor = Interceptor {
            chain -> val request = chain.request()
                .newBuilder()
                .addHeader("charset","UTF-8")
                .addHeader("token", MyMmkv.getString(Constants.token))
                .build()

            chain.proceed(request)
        }

        //Retrofit实例化
        retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initClient())
                .build()
    }

    /*
        OKHttp创建
     */
    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(LoggingInterceptor())
                //.addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
    }

   //TODO 日志拦截器
    class LoggingInterceptor:Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            var response = chain.proceed(request)
            var responseBody = response.peekBody(Long.MAX_VALUE)
            Log.i("responseBody",responseBody.string())
            return response
        }
    }

    //TODO  具体服务实例化
    //谁调用了create 谁决定T是什么类型 传入的是T 返回的也是T
    fun <T> create(service:Class<T>):T{
        return retrofit.create(service)
    }

    //TODO 这里确定了返回的结果类型 上面不确定 其他都一致
//    fun create():ServiceApi{
//        return retrofit.create(ServiceApi::class.java)
//    }
}