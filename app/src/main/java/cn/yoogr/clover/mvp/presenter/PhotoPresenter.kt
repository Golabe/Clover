package cn.yoogr.clover.mvp.presenter

import cn.yoogr.clover.base.BasePresenter
import cn.yoogr.clover.mvp.model.data.PhotoItemBean
import cn.yoogr.clover.mvp.view.IPhotoView
import com.avos.avoscloud.*
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVQuery
import com.avos.avoscloud.FindCallback

/**
 * Created by yuequan on 2017/11/27.
 */
class PhotoPresenter(val view: IPhotoView) : BasePresenter() {
    val TAG = "PhotoPresenter"
    val mPhotoItems = mutableListOf<PhotoItemBean>()

    /**
     * 获取图片列表数据
     */
    fun getPhotoListData(size: Int, skip: Int) {
        val query = AVQuery<AVObject>("Photo")
        query.include("author")
        query.whereEqualTo("status", 0)//查询状态是0的photo
        query.orderByDescending("createdAt")  //按时间升序排列
        query.limit(size)// 最多返回 size 条结果
        query.skip(skip)// 跳过 skip 条结果
        query.findInBackground(object : FindCallback<AVObject>() {
            override fun done(list: List<AVObject>?, e: AVException?) {
                // list 的结果为 status 等于0的图片的集合，当然我们知道现实中只存在一个广州市
                if (e == null) {
                    if (list!!.isNotEmpty()) {

                        list?.forEach {
                            view.onSuccess()
                            val title = it.getString("title")
                            val photoId = it.objectId
                            val imageUrl = it.getString("imageUrl")
                            val status = it.getInt("status")
                            val avObject = it.getAVObject<AVObject>("author")
                            val author = avObject.getString("username")
                            val avatarUrl = avObject.getString("avatarUrl")
                            val authorId = avObject.objectId
                            val itemBean = PhotoItemBean(title, photoId, imageUrl, status, author, avatarUrl, authorId)
                            mPhotoItems.add(itemBean)
                        }
                    }else view.isEmpty()
                } else view.onFailed(e.code)

            }
        })

    }
}