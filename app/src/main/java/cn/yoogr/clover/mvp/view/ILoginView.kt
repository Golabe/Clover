package cn.yoogr.clover.mvp.view

/**
 * Created by yuequan on 2017/11/27.
 */
interface ILoginView {

    fun onPhoneInputError()
    fun onCodeInputError()
    fun onLoginSuccess()
    fun onLoginFailed(msg:String)
    fun sendCodeSuccess()
    fun sendCodeFailed()
}