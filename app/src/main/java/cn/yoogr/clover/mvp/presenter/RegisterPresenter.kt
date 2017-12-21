package cn.yoogr.clover.mvp.presenter

import android.os.Handler
import android.util.Log
import buzz.pushfit.im.extention.isValidCode
import buzz.pushfit.im.extention.isValidPhone
import cn.yoogr.clover.base.BasePresenter
import cn.yoogr.clover.mvp.view.IRegisterView
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVOSCloud
import com.avos.avoscloud.RequestMobileCodeCallback
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.LogInCallback






/**
 * Created by yuequan on 2017/11/27.
 */
class RegisterPresenter(val view: IRegisterView) : BasePresenter() {
    val TAG = "RegisterPresenter"


    /**
     * 获取验证码
     */
    fun  sendCode(phone:String){

        if (phone.isValidPhone()) {
            AVOSCloud.requestSMSCodeInBackground(phone, object : RequestMobileCodeCallback() {
                override fun done(e: AVException?) {
                    if(e==null){
                        view.sendCodeSuccess()
                    }
                }
            })

        }else view.onPhoneInputError()

    }

    /**
     * 注册新用户
     */
    fun onRegister(phone: String, code: String) {

        //判断手机号码是否合法
        if (phone.isValidPhone()){
            //判断验证码是否合法
            if (code.isValidCode()){
                AVUser.signUpOrLoginByMobilePhoneInBackground(phone, code, object : LogInCallback<AVUser>() {
                    override fun done(p0: AVUser?, e: AVException?) {
                        if (e==null){
                            view.onRegisterSuccess()
                        }else view.onRegisterFailed(e.code)
                    }
                })

            }else view.onCodeInputError()

        }else view.onPhoneInputError()

    }

}