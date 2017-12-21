package cn.yoogr.clover.mvp.presenter

import cn.yoogr.clover.base.BasePresenter
import cn.yoogr.clover.mvp.view.IUploadPhotoView
import com.avos.avoscloud.*
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.lzx.nickphoto.utils.CommonUtil


/**
 * Created by yuequan on 2017/12/5.
 */
class UploadPhotoPresenter(val view: IUploadPhotoView) : BasePresenter() {


    /**
     * 上传图片
     */
    fun uploadPhoto(path: String, title: String) {
        val name = CommonUtil.getFileName(path)
        val file = AVFile.withAbsoluteLocalPath(name, path)
        file.saveInBackground(object : SaveCallback() {
            override fun done(e: AVException?) {
                // 成功或失败处理...
                if (e == null) {
                    val photoUrl = file.url
                    val photoId = file.objectId
                    val photoSize = file.size
                    val photoName = file.originalName
                    updatePhotoTab(photoUrl, photoId, photoSize, photoName, title)

                } else view.uploadFailed(e.code)

            }
        }, object : ProgressCallback() {
            override fun done(integer: Int?) {
                // 上传进度数据，integer 介于 0 和 100。
                view.uploadProgress(integer)
            }
        })
    }

    /**
     * 更新Photo表的图片数据
     */
    fun updatePhotoTab(photoUrl: String, photoId: String, size: Int, photoName: String, title: String) {


        val userId = AVUser.getCurrentUser().objectId
        val userObject = AVObject.createWithoutData("_User", userId)
        userObject.increment("photoTotal")
        userObject.isFetchWhenSave = true//图片数量+1
        val photo = AVObject("Photo")
        photo.put("imageUrl", photoUrl)
        photo.put("color", "#ffffff")
        photo.put("imageType", "#ffffff")
        photo.put("size", size)
        photo.put("place", "中山")
        photo.put("imageId", photoId)
        photo.put("imageName", photoName)
        photo.put("author", userObject)
        photo.put("title", title)
        photo.saveInBackground(object : SaveCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    view.updatePhotoTabSuccess()
                } else view.updatePhotoTabFailed(e.code)
            }
        })


    }
}