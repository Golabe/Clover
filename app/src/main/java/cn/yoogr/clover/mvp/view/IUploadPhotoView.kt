package cn.yoogr.clover.mvp.view

import cn.yoogr.clover.base.IBaseView

/**
 * Created by yuequan on 2017/12/5.
 */
interface IUploadPhotoView :IBaseView {

    fun uploadSuccess()
    fun uploadFailed(code:Int)
    fun uploadProgress(progress:Int?)
    fun updatePhotoTabSuccess()
    fun updatePhotoTabFailed(code: Int)
}