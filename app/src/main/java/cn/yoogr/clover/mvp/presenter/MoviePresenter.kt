package cn.yoogr.clover.mvp.presenter

import cn.yoogr.clover.base.BasePresenter
import cn.yoogr.clover.mvp.model.data.MovieItemBean
import cn.yoogr.clover.mvp.view.IMovieView
import com.avos.avoscloud.*

/**
 * Created by yuequan on 2017/11/27.
 */
class MoviePresenter(val view: IMovieView) : BasePresenter() {

    val movieItems = mutableListOf<MovieItemBean>()

    /**
     * 获取视频列表
     */
    fun getMovieListData(size: Int, skip: Int) {
        val query = AVQuery<AVObject>("Video")
        query.whereEqualTo("status", 0)//查询状态是0的movie
        query.orderByDescending("createdAt")  //按时间升序排列
        query.include("author")
        query.limit(size)// 最多返回 size 条结果
        query.skip(skip)// 跳过 skip 条结果
        query.findInBackground(object : FindCallback<AVObject>() {
            override fun done(list: MutableList<AVObject>?, e: AVException?) {

                if (e == null) {

                    if (list!!.isNotEmpty()) {

                        view.onSuccess()
                        list?.forEach {
                            val objectId = it.objectId
                            val videoUrl = it.getString("videoUrl")
                            val videoName = it.getString("videoName")
                            val videoSize = it.getNumber("videoSize")
                            val videoId = it.objectId
                            val videoType = it.getString("type")
                            val videoTitle = it.getString("title")
                            val avObject = it.getAVObject<AVObject>("author")
                            val avatarUrl = avObject.getString("avatarUrl")
                            val author = avObject.getString("username")
                            val authorId = avObject.objectId
                            val bean = MovieItemBean(authorId, videoUrl, videoName, videoSize, videoId, videoTitle, avatarUrl, author, videoType, objectId)
                            movieItems.add(bean)
                        }
                    }else view.isEmpty()

                } else view.onFailed(e.code)
            }
        })
    }

}
