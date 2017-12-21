package cn.yoogr.clover.mvp.presenter

import android.content.Context
import android.support.v7.app.AppCompatActivity
import cn.yoogr.clover.app.CloverConstant
import cn.yoogr.clover.base.BasePresenter
import cn.yoogr.clover.mvp.model.data.PhotoDetailsBean
import cn.yoogr.clover.mvp.view.IPhotoDetailsView
import cn.yoogr.clover.util.DownloadPictureUtil
import cn.yoogr.clover.util.SetWallpaperUtil
import com.avos.avoscloud.*
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.GetCallback
import com.avos.avoscloud.AVQuery
import com.avos.avoscloud.FindCallback
import com.lzx.nickphoto.utils.CommonUtil
import com.white.easysp.EasySP
import kotlinx.android.synthetic.main.layout_photo_details_navigation.*
import java.util.*
import java.util.Arrays.asList


/**
 * Created by yuequan on 2017/12/1.
 */
class PhotoDetailsPresenter(val view: IPhotoDetailsView) : BasePresenter() {
    /**
     * 获取图片详情
     */
    fun getPhotoDetailsData(photoId: String?) {

        val query = AVQuery<AVObject>("Photo")

        query.whereEqualTo("objectId", photoId)

        query.include("author")

        query.findInBackground(object : FindCallback<AVObject>() {
            override fun done(list: List<AVObject>?, e: AVException?) {

                if (e == null) {
                    list?.forEach {
                        val imageUrl = it.getString("imageUrl")
                        val imageName = it.getString("imageName")
                        val imageSize = it.getNumber("imageSize")
                        val color = it?.getString("color")
                        val likes = it?.getInt("likes")
                        val createAt = it?.createdAt
                        val objectId = it?.objectId
                        val download = it?.getInt("download")
                        val type = it?.getString("imageType")
                        val createLocation = it?.getString("place")
                        val avObject = it.getAVObject<AVObject>("author")
                        val avatarUrl = avObject.getString("avatarUrl")
                        val author = avObject.getString("username")
                        val authorId = avObject.objectId
                        val bean = PhotoDetailsBean(objectId, imageUrl, color, likes, imageName, imageSize, download, type, author, authorId, avatarUrl, createAt, createLocation)
                        view.onSuccess(bean)
                    }

                } else view.onFailed(e.code)

            }
        })

    }

    /**
     * 图片点赞功能
     * objectId 点赞图片唯一Id
     */
    fun like(photoId: String?) {
        val currentUserId = AVUser.getCurrentUser().objectId

        val saveLike = AVObject("Likes")
        saveLike.put("userId", currentUserId)
        saveLike.put("likeId", photoId)
        saveLike.saveInBackground(object : SaveCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    val theTodo = AVObject.createWithoutData("Photo", photoId)
                    // 原子增加查看的次数
                    theTodo.increment("likes")
                    theTodo.isFetchWhenSave = true
                    theTodo.saveInBackground(object : SaveCallback() {
                        override fun done(e: AVException?) {
                            if (e == null) {
                                view.likeSuccess()
                            } else view.onCountFailed(e.code)
                        }
                    })
                } else view.likeFailed(e.code)
            }
        })

    }

    /***
     * 图片下载
     * objectId下载图片唯一Id
     */
    fun download(context: Context, imageName: String, imageUrl: String, photoId: String?) {
        DownloadPictureUtil.savePicture(context, imageUrl, imageName, object : DownloadPictureUtil.SavePictureListener {
            override fun onSaved() {
                view.pictureSaved()
            }

            override fun onSuccess() {
                view.savePictureSuccess()
                countAdd("Photo", "download", photoId, 2)
            }

            override fun onFailed() {
                view.savePictureSuccess()
            }
        })
    }


    /**
     *计数功能
     * className  数据库表明
     * columnName 列名
     * objectId  对象唯一Id
     * type  点赞类型
     */
    private fun countAdd(className: String, columnName: String, photoId: String?, type: Int) {
        val theTodo = AVObject.createWithoutData(className, photoId)
        // 原子增加查看的次数
        theTodo.increment(columnName)
        theTodo.isFetchWhenSave = true
        theTodo.saveInBackground(object : SaveCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    view.onCountSuccess(type)
                } else view.onCountFailed(e.code)
            }
        })
    }

    /**
     * 获取当前用户对当前图片点赞状态
     */
    fun likeStatus(photoId: String?) {
        val currentUserId = AVUser.getCurrentUser().objectId
        val userQuery = AVQuery<AVObject>("Likes")
        userQuery.whereEqualTo("userId", currentUserId)
        val likeQuery = AVQuery<AVObject>("Likes")
        likeQuery.whereEqualTo("likeId", photoId)
        val andQuery = AVQuery.and(Arrays.asList(userQuery, likeQuery))
        andQuery.findInBackground(object : FindCallback<AVObject>() {
            override fun done(list: MutableList<AVObject>?, e: AVException?) {
                if (e == null) {
                    if (list!!.isEmpty()) {
                        view.like()
                    } else view.cancelLike()
                }
            }
        })

    }

    /**
     * 图片取消点赞
     */
    fun cancelLike(photoId: String?) {
        val currentUserId = AVUser.getCurrentUser().objectId
        val userQuery = AVQuery<AVObject>("Likes")
        userQuery.whereEqualTo("userId", currentUserId)
        val likeQuery = AVQuery<AVObject>("Likes")
        likeQuery.whereEqualTo("likeId", photoId)
        val andQuery = AVQuery.and(Arrays.asList(userQuery, likeQuery))
        andQuery.deleteAllInBackground(object : DeleteCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    val theTodo = AVObject.createWithoutData("Photo", photoId)
                    theTodo.increment("likes", -1)
                    theTodo.saveInBackground(object : SaveCallback() {
                        override fun done(e: AVException?) {
                            if (e == null) {
                                view.cancelLikeSuccess()
                            } else view.cancelLikeFailed(e.code)
                        }
                    })
                }
            }
        })


    }

    /**
     * 设置图片为手机桌面壁纸
     */
    fun setWallpaper(activity: AppCompatActivity, imageUrl: String, photoId: String) {
        //获取设置过壁纸的id
        val objectId = EasySP.init(activity).getString(CloverConstant.IS_SET_AS_WALLPAPER)
        //判断当前屏幕壁纸是否和当前图片为同一张图片
        if (objectId != photoId) {
            SetWallpaperUtil.INSTANCE.setWallpaper(activity, imageUrl, photoId, object : SetWallpaperUtil.SetWallpaperListener {
                override fun onSuccess() {
                    view.setWallpaperSuccess()
                }

                override fun onFailed() {
                    view.setWallpaperFailed()
                }
            })
        } else view.wallpaperHasBeenSet()


    }
}