package cn.yoogr.clover.mvp.presenter

import buzz.pushfit.im.extention.isValidCode
import buzz.pushfit.im.extention.isValidPhone
import cn.yoogr.clover.base.BasePresenter
import cn.yoogr.clover.mvp.view.ILoginView
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.RequestMobileCodeCallback
import com.avos.avoscloud.LogInCallback


/**
 * Created by yuequan on 2017/11/27.
 */
class LoginPresenter(val view: ILoginView) : BasePresenter() {
    /**
     * 发送验证码逻辑
     */
    fun sendCode(phone: String) {
        //检查手机号码是否合法
        if (phone.isValidPhone()) {
            AVUser.requestLoginSmsCodeInBackground(phone, object : RequestMobileCodeCallback() {
                override fun done(e: AVException?) {
                    if (e == null) {
                        view.sendCodeSuccess()
                    }
                }
            })
        } else view.onPhoneInputError()
    }

    /**
     * 登录逻辑
     */
    fun onLogin(phone: String, code: String) {
        if (phone.isValidPhone()) {
            if (code.isValidCode()) {
                AVUser.signUpOrLoginByMobilePhoneInBackground(phone, code, object : LogInCallback<AVUser>() {
                    override fun done(u: AVUser?, e: AVException?) {
                        if (e != null) {
                          view.onLoginFailed("登录失败")
                        } else   view.onLoginSuccess()
                    }
                })
            } else view.onCodeInputError()

        } else view.onPhoneInputError()

    }
}
