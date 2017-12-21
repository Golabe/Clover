package cn.yoogr.clover.mvp.view

import cn.yoogr.clover.base.IBaseView
import cn.yoogr.clover.mvp.model.data.CloverDetailsBean

/**
 * Created by yuequan on 2017/12/8.
 */
interface ICloverDetailsView :IBaseView {
    fun onSuccess(bean: CloverDetailsBean)
    fun onFailed(code: Int)
}