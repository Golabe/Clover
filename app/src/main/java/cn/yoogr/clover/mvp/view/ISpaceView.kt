package cn.yoogr.clover.mvp.view

import cn.yoogr.clover.base.IBaseView
import cn.yoogr.clover.mvp.model.data.UserInfoBean

/**
 * Created by yuequan on 2017/12/2.
 */
interface ISpaceView :IBaseView {

    fun getUserInfoSuccess(data: UserInfoBean)
}