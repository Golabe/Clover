package cn.yoogr.clover.mvp.view

import cn.yoogr.clover.base.IBaseView
import cn.yoogr.clover.mvp.model.data.VideoDetailsBean

/**
 * Created by yuequan on 2017/12/4.
 */
interface IMovieDetailsView :IBaseView {
    fun onFailed(code: Int)
    fun onSuccess(bean: VideoDetailsBean)
}