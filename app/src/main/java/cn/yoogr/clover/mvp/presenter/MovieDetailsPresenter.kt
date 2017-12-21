package cn.yoogr.clover.mvp.presenter

import cn.yoogr.clover.R.id.author
import cn.yoogr.clover.base.BasePresenter
import cn.yoogr.clover.mvp.model.data.VideoDetailsBean
import cn.yoogr.clover.mvp.view.IMovieDetailsView
import com.avos.avoscloud.*

/**
 * Created by yuequan on 2017/12/4.
 */
class MovieDetailsPresenter(val view: IMovieDetailsView) : BasePresenter() {

    /**
     * 获取视频详情
     */
    fun getMovieListData(objectId:String){

        val query = AVQuery<AVObject>("Video")

        query.whereEqualTo("objectId", objectId)

        query.include("author")

        query.findInBackground(object : FindCallback<AVObject>() {
            override fun done(list: List<AVObject>?, e: AVException?) {

                if (e == null) {
                    list?.forEach {
                        val createdAt = it.createdAt
                        val place = it.getString("place")
                        val type = it.getString("type")
                        val likes = it.getNumber("likes")
                        val title = it.getString("title")
                        val size = it.getNumber("size")
                        val avUser = it.getAVUser<AVUser>("author")
                        val author = avUser.username
                        val authorId = avUser.objectId
                        val avatarUrl = avUser.getString("avatarUrl")
                        val videoUrl = it.getString("videoUrl")
                        val watch = it.getNumber("watch")
                        val download = it.getNumber("download")
                        val videoName = it.getString("videoName")
                        val bean = VideoDetailsBean(createdAt,place, type, likes, size, title, author, authorId, avatarUrl, videoUrl, watch, download, videoName)

                        view.onSuccess(bean)
                    }

                } else view.onFailed(e.code)

            }
        })
    }
}