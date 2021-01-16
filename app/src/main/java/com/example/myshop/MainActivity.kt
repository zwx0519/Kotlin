package com.example.myshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myshop.test.more_view.More_ViewActivity
import com.example.myshop.ui.shop.ShopActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLinstener()
    }

    //TODO 点击事件
    private fun initLinstener() {
        btn_shop.setOnClickListener(this)
        btn_more.setOnClickListener(this)
    }

    //实现View.OnClickListener接口的onClick方法
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_shop -> {
                val intent = Intent(
                    this,
                    ShopActivity::class.java
                )
                startActivity(intent)
            }
            R.id.btn_more -> {
                val intent = Intent(
                    this,
                    More_ViewActivity::class.java
                )
                startActivity(intent)
            }

        }
    }
}