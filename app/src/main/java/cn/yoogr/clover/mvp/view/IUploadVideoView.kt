package cn.yoogr.clover.mvp.view

import cn.yoogr.clover.base.IBaseView

/**
 * Created by yuequan on 2017/12/6.
 */
interface IUploadVideoView :IBaseView {
    fun uploadProgress(integer: Int?)
    fun uploadFailed(code: Int)
    fun updateVideoTabSuccess()
    fun updateVideoTabFailed(code: Int)
}