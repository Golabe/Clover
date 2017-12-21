package cn.yoogr.clover.mvp.view

import cn.yoogr.clover.base.IBaseView
import cn.yoogr.clover.mvp.model.data.AuthorInfoBean

/**
 * Created by yuequan on 2017/12/2.
 */
interface IAuthorView:IBaseView {
    fun getAuthorInfoSuccess(data:AuthorInfoBean)
    fun getAuthorInfoFailed(code:Int)

    fun following()
    fun cancelFollow()
    fun followFailed(code: Int)
    fun onCountFailed(code: Int)
    fun followSuccess()
    fun cancelFollowSuccess()
    fun cancelFollowFailed(code: Int)
}