package cn.yoogr.clover.mvp.presenter

import cn.yoogr.clover.base.BasePresenter
import cn.yoogr.clover.mvp.model.data.CloverDetailsBean
import cn.yoogr.clover.mvp.view.ICloverDetailsView
import com.avos.avoscloud.*

/**
 * Created by yuequan on 2017/12/8.
 *
 * Clover 详情
 */
class CloverDetailsPresenter(val view: ICloverDetailsView) : BasePresenter() {

    /**
     * 获取Clover详情数据
     */
    fun getCloverDetailsData(cloverId:String){

        val query = AVQuery<AVObject>("Clover")
        query.whereEqualTo("objectId", cloverId)
        query.include("author")
        query.findInBackground(object : FindCallback<AVObject>() {
            override fun done(list: List<AVObject>?, e: AVException?) {

                if (e == null) {
                    list?.forEach {

                        val coverUrl = it.getString("coverUrl")
                        val content = it.getString("content")
                        val likes = it.getNumber("likes")
                        val title = it.getString("title")
                        val avObject = it.getAVObject<AVObject>("author")
                        val author = avObject.getString("username")
                        val authorId = avObject.objectId
                        val avatarUrl = avObject.getString("avatarUrl")
                        val createdAt = it.createdAt


                        val bean = CloverDetailsBean(coverUrl, content, likes, title, author, authorId, avatarUrl, createdAt)

                        view.onSuccess(bean)
                    }

                } else view.onFailed(e.code)

            }
        })


    }
}