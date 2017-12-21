package cn.yoogr.clover.mvp.view

import cn.yoogr.clover.base.IBaseView

/**
 * Created by yuequan on 2017/12/7.
 */
interface ICloverView :IBaseView {
    fun onSuccess()
    fun isEmpty()
    fun onFailed(code: Int)
}