package cn.yoogr.clover.mvp.view

import cn.yoogr.clover.base.IBaseView

/**
 * Created by yuequan on 2017/11/27.
 */
interface IRegisterView : IBaseView {

    fun onPhoneInputError()
    fun onCodeInputError()
    fun onRegisterSuccess()
    fun onRegisterFailed(status:Int)

    fun sendCodeSuccess()
    fun sendCodeFailed()
}