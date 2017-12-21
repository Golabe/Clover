package cn.yoogr.clover.mvp.view

import cn.yoogr.clover.base.IBaseView

/**
 * Created by yuequan on 2017/12/6.
 */
interface ICloverEditView :IBaseView {

    fun uploadImageSuccess(url:String,name:String,objectId:String)
    fun uploadImageFailed(code:Int)
    fun uploadImageProgress(integer: Int?)
    fun pushSuccess()
    fun pushFailed(code: Int)
}