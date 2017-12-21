package cn.yoogr.clover.mvp.presenter

import cn.yoogr.clover.base.BasePresenter
import cn.yoogr.clover.mvp.model.data.UserInfoBean
import cn.yoogr.clover.mvp.view.ISpaceView
import com.avos.avoscloud.*
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.GetCallback




/**
 * Created by yuequan on 2017/12/2.
 */
class SpacePresenter(val view: ISpaceView) : BasePresenter() {


    /**
     * 获取用户信息
     */
    fun getUserInfo() {
        val currentUser = AVUser.getCurrentUser()
        if (currentUser != null) {
            val userId = currentUser.objectId
            val userQuery = AVQuery<AVObject>("_User")
            userQuery.getInBackground(userId,object :GetCallback<AVObject>(){
                override fun done(avQuery: AVObject?, e: AVException?) {
                    if (e == null) {
                        val avatarUrl = avQuery?.getString("avatarUrl")
                        val background =avatarUrl
                        val birthday=avQuery?.getString("birthday")
                        val username = avQuery?.getString("username")
                        val nickname = avQuery?.getString("nickName")
                        val videoTotal = avQuery?.getInt("videoTotal")
                        val photoTotal = avQuery?.getInt("photoTotal")
                        val cloverTotal = avQuery?.getInt("cloverTotal")
                        val follow = avQuery?.getInt("follow")
                        val bean = UserInfoBean(username, nickname, avatarUrl, background, videoTotal, photoTotal, cloverTotal, follow,birthday)
                        view.getUserInfoSuccess(bean)
                    }
                }
            })
        }
    }

    /**
     * 退出登录
     */
    fun logout() {
        AVUser.logOut()// 清除缓存用户对象
    }
}