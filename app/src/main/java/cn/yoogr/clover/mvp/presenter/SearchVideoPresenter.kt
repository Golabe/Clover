package cn.yoogr.clover.mvp.presenter

import cn.yoogr.clover.base.BasePresenter
import cn.yoogr.clover.mvp.model.data.SearchVideoBean
import cn.yoogr.clover.mvp.view.ISearchContentView
import com.avos.avoscloud.*

/**
 * Created by yuequan on 2017/12/8.
 */
class SearchVideoPresenter(val  view: ISearchContentView) : BasePresenter() {

    val searchVideoItems = mutableListOf<SearchVideoBean>()

    /**
     * 搜索视频
     */
    fun searchVideo(search: String,size:Int,skip:Int) {
        val query = AVQuery<AVObject>("Video")
        query.include("author")
        query.whereEqualTo("status", 0)//查询状态是0的photo
        query.orderByDescending("createdAt")  //按时间升序排列
        query.limit(size)// 最多返回 size 条结果
        query.skip(skip)// 跳过 skip 条结果
        query.whereContains("title", search)
        query.findInBackground(object : FindCallback<AVObject>() {
            override fun done(list: MutableList<AVObject>?, e: AVException?) {
                if (e == null) {
                    if (list!!.isNotEmpty()){
                        list?.forEach {
                            val avObject = it.getAVObject<AVObject>("author")
                            val avatarUrl = avObject.getString("avatarUrl")
                            val authorId = avObject.objectId
                            val videoUrl = it.getString("videoUrl")
                            val objectId = it.objectId
                            val type = it.getString("type")
                            val title = it.getString("title")
                            val bean = SearchVideoBean(authorId, avatarUrl, videoUrl, objectId, type,title)
                            searchVideoItems.add(bean)
                            view.searchSuccess()
                        }


                    }else view.isEmpty()
                } else view.searchFailed(e.code)
            }
        })

    }

}