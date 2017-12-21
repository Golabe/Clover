package cn.yoogr.clover.mvp.presenter

import cn.yoogr.clover.base.BasePresenter
import cn.yoogr.clover.mvp.view.ICloverEditView
import android.os.Environment.getExternalStorageDirectory
import com.avos.avoscloud.*
import com.lzx.nickphoto.utils.CommonUtil


/**
 * Created by yuequan on 2017/12/6.
 */
class CloverEditPresenter(val view: ICloverEditView) : BasePresenter() {

    /**
     * 上传编辑中的图片
     */
    fun uploadImage(path: String?) {
        val fileName = CommonUtil.getFileName(path!!)
        val file = AVFile.withAbsoluteLocalPath(fileName, path)
        file.saveInBackground(object : SaveCallback() {
            override fun done(e: AVException?) {
                // 成功或失败处理...
                if (e == null) {
                    val url = file.url
                    val name = file.originalName
                    val objectId = file.objectId
                    view.uploadImageSuccess(url, name, objectId)
                } else view.uploadImageFailed(e.code)

            }
        }, object : ProgressCallback() {
            override fun done(integer: Int?) {
                // 上传进度数据，integer 介于 0 和 100。
                view.uploadImageProgress(integer)
            }
        })
    }

    /**
     * 如果文章没有上传 或者没有保存为草稿  则删除编辑过程中上传的照片
     */
    fun deleteEditImage() {

    }

    /**
     * 发布文章
     */
    fun push(content: String?, title: String?, coverUrl: String?) {

        val userId = AVUser.getCurrentUser().objectId
        val userObject = AVObject.createWithoutData("_User", userId)
        userObject.increment("cloverTotal")
        userObject.isFetchWhenSave = true//图片数量+1

        val clover = AVObject("Clover")
        clover.put("content", content)
        clover.put("title", title)
        clover.put("coverUrl", coverUrl)
        clover.put("author", userObject)
        clover.put("title", title)
        clover.saveInBackground(object : SaveCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    view.pushSuccess()
                } else view.pushFailed(e.code)
            }
        })

    }
}