package cn.yoogr.clover.mvp.presenter

import cn.yoogr.clover.base.BasePresenter
import cn.yoogr.clover.mvp.view.IUploadVideoView
import com.avos.avoscloud.*
import com.lzx.nickphoto.utils.CommonUtil


/**
 * Created by yuequan on 2017/12/6.
 */
class UploadVideoPresenter(val view: IUploadVideoView) : BasePresenter() {

    /**
     * 上传视频
     */
    fun uploadVideo(path:String,title:String){
        val fileName = CommonUtil.getFileName(path)
        val file = AVFile.withAbsoluteLocalPath(fileName,path )
        file.saveInBackground(object : SaveCallback() {
            override fun done(e: AVException?) {
                // 成功或失败处理...
                if (e == null) {
                    val videoUrl = file.url
                    val videoId = file.objectId
                    val videoSize = file.size
                    val videoName = file.originalName
                    updateVideoTab(videoUrl,videoId,videoSize,videoName,title)//更新Video表数据
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
     * 更新Video表的图片数据
     */
    fun updateVideoTab(videoUrl: String, videoId: String, size: Int, videoName: String, title: String) {
        val userId = AVUser.getCurrentUser().objectId
        val userObject = AVObject.createWithoutData("_User", userId)
        userObject.increment("videoTotal")
        userObject.isFetchWhenSave = true//视频数量+1
        val photo = AVObject("Video")
       // photo.put("imageUrl",imageUrl)
        photo.put("videoUrl", videoUrl)
        photo.put("type", "风景")
        photo.put("size", size)
        photo.put("place", "中山")
        photo.put("videoId", videoId)
        photo.put("videoName", videoName)
        photo.put("author", userObject)
        photo.put("title", title)
        photo.saveInBackground(object : SaveCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    view.updateVideoTabSuccess()
                } else view.updateVideoTabFailed(e.code)
            }
        })


    }

}