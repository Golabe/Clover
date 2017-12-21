package cn.yoogr.clover.mvp.presenter

import cn.yoogr.clover.base.BasePresenter
import cn.yoogr.clover.mvp.model.data.CloverItemBean
import cn.yoogr.clover.mvp.view.ICloverView
import com.avos.avoscloud.*

/**
 * Created by yuequan on 2017/11/27.
 */
class CloverPresenter(val view:ICloverView) : BasePresenter() {

    val cloverItems= mutableListOf<CloverItemBean>()

    /**
     * 获取Clover列表数据
     */
    fun getCloverListData(size:Int,skip:Int){
        val query = AVQuery<AVObject>("Clover")
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
                            val cloverId = it.objectId
                            val createdAt = it.createdAt
                            val title = it.getString("title")
                            val coverUrl = it.getString("coverUrl")
                            val avObject = it.getAVObject<AVObject>("author")
                            val avatarUrl = avObject.getString("avatarUrl")
                            val author = avObject.getString("username")
                            val authorId = avObject.objectId
                            val bean = CloverItemBean(cloverId, createdAt, title, coverUrl, author, authorId, avatarUrl)
                            cloverItems.add(bean)
                        }
                    }else view.isEmpty()

                } else view.onFailed(e.code)
            }
        })
    }
}