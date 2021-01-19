package com.example.myshop.ui.shop.me

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myshop.R
import com.example.myshop.base.BaseFragment
import com.example.myshop.databinding.FragmentMeBinding
import com.example.myshop.ui.shop.me.login.MeLoginActivity
import com.example.myshop.ui.shop.me.userinfo.UserInfoActivity
import com.example.myshop.utils.ImageLoader
import com.shop.utils.SpUtils
import com.shop.viewmodel.mine.MeViewModel
import kotlinx.android.synthetic.main.fragment_me.*

class MeFragment : BaseFragment<MeViewModel, FragmentMeBinding>
    (R.layout.fragment_me,MeViewModel::class.java),View.OnClickListener  {
    var token:String? = null

    //采用伴生对象 companion object==static 保证只加载一次
    companion object{
        val instance by lazy { MeFragment() }
    }

    override fun initView() {
        initClick()
        token = SpUtils.instance!!.getString("token")
    }

    //TODO 点击事件
    fun initClick(){
        mDataBinding!!.ivMyImg.setOnClickListener(this)
        mDataBinding!!.tvMyLogin.setOnClickListener(this)
        mDataBinding!!.ivMyReturn.setOnClickListener(this)
        mDataBinding!!.llFiveCollect.setOnClickListener(this)
    }

    override fun initVM() {


    }

    override fun initData() {
        if(!TextUtils.isEmpty(token)){
            isLogin(true)
        }else{
            isLogin(false)
        }
    }

    override fun initVariable() {

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_my_img -> {       //头像图片
                initLogin()
            }
            R.id.tv_my_login -> { //点击登录
                initLogin()
            }
            R.id.iv_my_return -> {   //退出登录

            }
            R.id.ll_five_collect -> {    //收藏
                //跳转界面
            }

        }
    }

    //判断是否登录
    fun initLogin(){
        if(!TextUtils.isEmpty(token)){
            //进入个人主页
            openUserInfoDetail()
        }else{
            //跳转登录界面
            val intent = Intent(activity, MeLoginActivity::class.java)
            startActivity(intent)
            isLogin(false)
        }
    }

    //TODO 进入个人主页
    fun openUserInfoDetail(){
        val intent = Intent(activity, UserInfoActivity::class.java)
        startActivity(intent)
        isLogin(true)
    }

    /**
     * 登录状态的界面控制
     * @param bool
     */
    private fun isLogin(bool: Boolean) {
        if (bool) {     //登录
            tv_my_login!!.visibility = View.GONE      //点击登录
            tv_my_head_nickname!!.visibility = View.VISIBLE   //昵称
            tv_my_head_mark!!.visibility = View.VISIBLE       //签名

            var username = SpUtils.instance!!.getString("username")     //名称
            var nickname = SpUtils.instance!!.getString("nickname")     //昵称
            var birthday = SpUtils.instance!!.getString("birthday")     //生日
            var avatar = SpUtils.instance!!.getString("avatar")     //头像

            if(!TextUtils.isEmpty(nickname)){
                tv_my_head_nickname.setText(nickname)
            }else{
                tv_my_head_nickname.setText(username)
            }
            tv_my_head_mark.setText(birthday)
            ImageLoader.loadImage(avatar,iv_my_img)
            if(!TextUtils.isEmpty(avatar)){
                Glide.with(this).load(avatar).apply(RequestOptions().circleCrop()).into(iv_my_img)
            }

        }else{      //未登录
            tv_my_login!!.visibility = View.VISIBLE      //点击登录
            tv_my_head_nickname!!.visibility = View.GONE   //昵称
            tv_my_head_mark!!.visibility = View.GONE       //签名
            Glide.with(this).load(R.mipmap.c4).apply(RequestOptions().circleCrop()).into(iv_my_img)
        }

    }
}