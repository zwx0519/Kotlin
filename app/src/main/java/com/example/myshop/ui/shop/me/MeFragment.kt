package com.example.myshop.ui.shop.me

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myshop.R
import com.example.myshop.base.BaseFragment
import com.example.myshop.databinding.FragmentMeBinding
import com.example.myshop.ui.shop.me.login.MeLoginActivity
import com.example.myshop.ui.shop.me.userinfo.UserInfoActivity
import com.example.myshop.utils.ActivityCollectorUtil
import com.example.myshop.utils.ImageLoader
import com.example.myshop.utils.ToastUtils
import com.shop.app.MyApp
import com.shop.utils.SpUtils
import com.shop.viewmodel.mine.MeViewModel
import kotlinx.android.synthetic.main.fragment_me.*

class MeFragment : BaseFragment<MeViewModel, FragmentMeBinding>
    (R.layout.fragment_me,MeViewModel::class.java),View.OnClickListener  {

    //采用伴生对象 companion object==static 保证只加载一次
    companion object{
        val instance by lazy { MeFragment() }
    }

    override fun initView() {
        initClick()//点击事件
    }

    //立即加载
    override fun onResume() {
        super.onResume()
        initData()
    }

    //TODO 点击事件
    fun initClick(){
        mDataBinding!!.ivMyImg.setOnClickListener(this)
        mDataBinding!!.tvMyLogin.setOnClickListener(this)
        mDataBinding!!.ivMyReturn.setOnClickListener(this)
        mDataBinding!!.llFiveCollect.setOnClickListener(this)
    }

    //懒加载
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            initData()
            initLogin()
        }
    }

    override fun initVM() {
    }

    override fun initData() {
        var token = SpUtils.instance!!.getString("token")

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
                val intent = Intent(activity, UserInfoActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_my_login -> { //点击登录
                initLogin()
            }
            R.id.iv_my_return -> {   //退出登录
                SpUtils.instance!!.remove("token")

                //退出登录
                ActivityCollectorUtil().finishAllActivity()

                //关闭页面
                // finishAndRemoveTask();
                isLogin(false)
            }
            R.id.ll_five_collect -> {    //收藏
                //跳转界面
            }

        }
    }

    //TODO 判断是否登录
    fun initLogin(){
        var token = SpUtils.instance!!.getString("token")
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
        ToastUtils.s(activity!!, "此用户已登录")
        val intent = Intent(activity, UserInfoActivity::class.java)

        val txtName: String = tv_my_head_nickname.getText().toString() //姓名
        val txtMark: String = tv_my_head_mark.getText().toString() //签名

        MyApp.map!!.put("txtName",txtName)
        MyApp.map!!.put("txtMark", txtMark)

        startActivity(intent)
        isLogin(true)
    }

   //  TODO 登录状态的界面控制
    private fun isLogin(bool: Boolean) {
        if (bool) {     //登录
            tv_my_login!!.visibility = View.GONE      //点击登录
            tv_my_head_nickname!!.visibility = View.VISIBLE   //昵称
            tv_my_head_mark!!.visibility = View.VISIBLE       //签名

            var username = SpUtils.instance!!.getString("username")     //名称
            var nickname = SpUtils.instance!!.getString("nickname")     //昵称
            var birthday = SpUtils.instance!!.getString("birthday")     //生日
            var avatar = SpUtils.instance!!.getString("avatar")     //头像
            val mark = SpUtils.instance!!.getString("mark")
            if(!TextUtils.isEmpty(nickname)){
                tv_my_head_nickname.setText(nickname)
            }else{
                tv_my_head_nickname.setText(username)
            }
            //名字
            tv_my_head_mark.setText(avatar)
            //头像
            ImageLoader.loadImage(mark,iv_my_img)
            val img= SpUtils.instance!!.getString("img")
            if(!TextUtils.isEmpty(img)){
                Glide.with(this).load(img).apply(RequestOptions().circleCrop()).into(iv_my_img)
            }

        }else{      //未登录
            tv_my_login!!.visibility = View.VISIBLE      //点击登录
            tv_my_head_nickname!!.visibility = View.GONE   //昵称
            tv_my_head_mark!!.visibility = View.GONE       //签名
            Glide.with(this).load(R.mipmap.c4).apply(RequestOptions().circleCrop()).into(iv_my_img)
        }

    }
}