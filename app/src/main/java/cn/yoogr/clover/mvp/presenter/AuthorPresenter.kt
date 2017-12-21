package cn.yoogr.clover.mvp.presenter

import cn.yoogr.clover.base.BasePresenter
import cn.yoogr.clover.mvp.model.data.AuthorInfoBean
import cn.yoogr.clover.mvp.view.IAuthorView
import com.avos.avoscloud.*
import java.util.*


/**
 * Created by yuequan on 2017/11/27.
 */
class AuthorPresenter(val view: IAuthorView) : BasePresenter() {
    /**
     * 获取作者信息
     */
    fun getAuthorInfo(authorId: String) {
        val userQuery = AVQuery<AVUser>("_User")
        userQuery.getInBackground(authorId, object : GetCallback<AVUser>() {
            override fun done(avUser: AVUser?, e: AVException?) {
                if (e == null) {
                    val username = avUser?.getString("username")
                    val following = avUser?.getInt("following")
                    val email = avUser?.getString("email")
                    val follow = avUser?.getInt("follow")
                    val avFile = avUser?.getAVFile<AVFile>("headerImage")
                    val headerImage = avFile?.url
                    val photoTotal = avUser?.getInt("photoTotal")
                    val cloverTotal = avUser?.getInt("cloverTotal")
                    val videoTotal = avUser?.getInt("videoTotal")
                    val nickName = avUser?.getString("nickName")
                    val avatarUrl = avUser?.getString("avatarUrl")
                    val bean = AuthorInfoBean(following, photoTotal, email, follow, headerImage, username, cloverTotal, nickName, videoTotal, avatarUrl)
                    view.getAuthorInfoSuccess(bean)
                } else view.getAuthorInfoFailed(e.code)
            }
        })

    }

    /**
     * 获取关注状态
     */
    fun getFollowStatus(authorId:String):Boolean{
        var flag=false
        if (AVUser.getCurrentUser()!=null){
            val currentUserId = AVUser.getCurrentUser().objectId
            val userQuery = AVQuery<AVObject>("Follows")
            userQuery.whereEqualTo("followerId", currentUserId)//关注者id
            val likeQuery = AVQuery<AVObject>("Follows")
            likeQuery.whereEqualTo("followedId", authorId)//被关注者Id
            val andQuery = AVQuery.and(Arrays.asList(userQuery, likeQuery))
            andQuery.findInBackground(object : FindCallback<AVObject>() {
                override fun done(list: MutableList<AVObject>?, e: AVException?) {
                    if (e == null) {
                        if (list!!.isEmpty()) {
                            view.following()
                            flag=true
                        }else view.cancelFollow()
                    }
                }
            })
        }

        return flag
    }

    /**
     * 关注
     */
    fun follow(authorId: String){
        val currentUserId = AVUser.getCurrentUser().objectId
        val saveLike = AVObject("Follows")
        saveLike.put("followerId", currentUserId)
        saveLike.put("followedId", authorId)
        saveLike.saveInBackground(object : SaveCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    val theTodo = AVObject.createWithoutData("_User", authorId)
                    // 原子增加查看的次数
                    theTodo.increment("follow")
                    theTodo.isFetchWhenSave = true
                    theTodo.saveInBackground(object : SaveCallback() {
                        override fun done(e: AVException?) {
                            if (e == null) {
                                view.followSuccess()
                            } else view.onCountFailed(e.code)
                        }
                    })
                } else view.followFailed(e.code)
            }
        })
    }

    /**
     * 取消关注
     */
    fun cancelFollow(authorId: String){
        val currentUserId = AVUser.getCurrentUser().objectId
        val userQuery = AVQuery<AVObject>("Follows")
        userQuery.whereEqualTo("followerId", currentUserId)
        val likeQuery = AVQuery<AVObject>("Follows")
        likeQuery.whereEqualTo("followedId", authorId)
        val andQuery = AVQuery.and(Arrays.asList(userQuery, likeQuery))
        andQuery.deleteAllInBackground(object : DeleteCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    val theTodo = AVObject.createWithoutData("_User", authorId)
                    theTodo.increment("follow", -1)
                    theTodo.saveInBackground(object : SaveCallback() {
                        override fun done(e: AVException?) {
                            if (e == null) {
                                view.cancelFollowSuccess()
                            } else view.cancelFollowFailed(e.code)
                        }
                    })
                }
            }
        })
    }
}