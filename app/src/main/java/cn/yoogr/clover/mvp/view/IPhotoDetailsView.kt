package cn.yoogr.clover.mvp.view

import cn.yoogr.clover.base.IBaseView
import cn.yoogr.clover.mvp.model.data.PhotoDetailsBean

/**
 * Created by yuequan on 2017/12/1.
 */
interface IPhotoDetailsView : IBaseView {

    fun onSuccess(data: PhotoDetailsBean)
    fun onFailed(code: Int)

    fun onCountSuccess(type: Int)
    fun onCountFailed(code: Int)

    fun likeSuccess()
    fun likeFailed(code: Int)

    fun like()
    fun cancelLike()

    fun cancelLikeSuccess()
    fun cancelLikeFailed(code: Int)

    fun savePictureSuccess()
    fun savePictureFailed()
    fun pictureSaved()


    fun setWallpaperSuccess()
    fun setWallpaperFailed()
    fun wallpaperHasBeenSet()
}