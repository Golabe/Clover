package cn.yoogr.clover.mvp.view

import cn.yoogr.clover.base.IBaseView
import cn.yoogr.clover.mvp.model.data.VersionBean

/**
 * Created by yuequan on 2017/11/26.
 */
interface IMainView : IBaseView {
    fun noLogin()
    fun loginIn()

    //有新版本
    fun hasVersion(result: VersionBean)

    fun getAvatarSuccess(url: String?)
    fun getAvatarFailed()

}