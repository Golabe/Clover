package cn.yoogr.clover.mvp.view

import cn.yoogr.clover.base.IBaseView

/**
 * Created by yuequan on 2017/12/4.
 */
interface IMovieView :IBaseView {

    fun onSuccess()
    fun onFailed(code:Int)
    fun isEmpty()
}