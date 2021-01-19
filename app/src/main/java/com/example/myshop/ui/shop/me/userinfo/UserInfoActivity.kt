package com.example.myshop.ui.shop.me.userinfo

import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.alibaba.sdk.android.oss.OSS
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myshop.R
import com.example.myshop.databinding.ActivityUserInfoBinding
import com.example.myshop.utils.SystemUtils
import com.example.myshop.viewmodel.shop.me.login.MeUserInfoViewModel
import com.shop.base.BaseActivity
import com.shop.utils.SpUtils
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : BaseActivity<MeUserInfoViewModel, ActivityUserInfoBinding>(
    R.layout.activity_user_info,
    MeUserInfoViewModel::class.java
), View.OnClickListener {
    //private val ossClient: OSS? = null

    var nickname_1: String? = null
    var birthday_1: String? = null

    override fun initView() {
        initClick()
        var sp_username = SpUtils.instance!!.getString("username")     //名称
        var sp_nickname = SpUtils.instance!!.getString("nickname")     //昵称
        var sp_birthday = SpUtils.instance!!.getString("birthday")     //生日
        var sp_avatar = SpUtils.instance!!.getString("avatar")     //头像


        //赋值
        tv_UserInfo_username.setText(sp_username)
        tv_UserInfo_nickname.setText(sp_nickname)
        tv_UserInfo_birthday.setText(sp_birthday)
        if (!TextUtils.isEmpty(sp_avatar)) {
            Glide.with(this).load(sp_avatar).apply(RequestOptions().circleCrop())
                .into(iv_UserInfo_avatar!!)
        }
    }


    fun initClick() {
        mDataBinding!!.ivUserInfoAvatar.setOnClickListener(this)
        mDataBinding!!.layoutNickname.setOnClickListener(this)
        mDataBinding!!.layoutBirthday.setOnClickListener(this)
    }


    override fun initVM() {
        mViewModel.meuserinfo.observe(this, Observer {
            Log.e("TAG", "initVM: " + "成功")
            if (it != null && it.size > 0) {
                SystemUtils.closeSoftKeyBoard(this)
                layout_input!!.visibility = View.GONE
                SpUtils.instance!!.setValue("nickname", nickname_1)
                SpUtils.instance!!.setValue("birthday", birthday_1)
            }
        })
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_UserInfo_avatar -> {       //头像图片
                ImgAvater()
            }
            R.id.layout_nickname -> {       //昵称
                NickName()
            }
            R.id.layout_birthday -> {       //生日
                Birthday()
            }
        }
    }

    //TODO 生日
    private fun Birthday() {
        updateName()//打开软键盘
        btn_save!!.setOnClickListener {
            birthday_1 = txt_input.text.toString()
            if (!TextUtils.isEmpty(birthday_1)) {
                val map = HashMap<String, String>()
                map["birthday"] = birthday_1!!
                mViewModel.MeUserInfo(map)
                layout_input.visibility = View.GONE
                tv_UserInfo_birthday.setText(birthday_1)
                txt_input.setText("")
            }
        }
    }

    //TODO 昵称
    private fun NickName() {
        updateName()//打开软键盘
        btn_save!!.setOnClickListener {
            nickname_1 = txt_input.text.toString()
            if (!TextUtils.isEmpty(nickname_1)) {
                val map = HashMap<String, String>()
                map["nickname"] = nickname_1!!
                mViewModel.MeUserInfo(map)
                layout_input.visibility = View.GONE
                tv_UserInfo_nickname.setText(nickname_1)
                txt_input.setText("")
            }
        }
    }

    //TODO 打开软键盘
    private fun updateName() {
        layout_input!!.visibility = View.VISIBLE
        txt_input!!.isFocusable = true
        SystemUtils.openSoftKeyBoard(this)
    }


    //TODO 头像图片
    private fun ImgAvater() {

    }

    override fun initData() {

    }

    override fun initVariable() {

    }
}