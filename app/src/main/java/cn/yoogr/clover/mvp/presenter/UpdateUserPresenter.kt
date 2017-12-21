package cn.yoogr.clover.mvp.presenter

import android.os.Environment
import cn.yoogr.clover.base.BasePresenter
import cn.yoogr.clover.mvp.view.IUserView
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.SaveCallback
import com.avos.avoscloud.AVFile

/**
 * Created by yuequan on 2017/11/28.
 */
class UpdateUserPresenter(val view: IUserView) : BasePresenter() {


    companion object {
        val currentUsername = AVUser.getCurrentUser().username!!
    }


    /**
     * 上传头像
     */
    fun uploadAvatar(){
        val file = AVFile.withAbsoluteLocalPath(" avatar.jpg", Environment.getExternalStorageDirectory().canonicalPath + "/Clover/image/avatar.jpg")
        file.saveInBackground(object : SaveCallback() {
            override fun done(e: AVException?) {
                // 成功或失败处理...
                if (e==null){
                    AVUser.getCurrentUser().put("avatarUrl", file.url)
                    AVUser.getCurrentUser().saveInBackground()
                }
            }
        })
    }
    /**
     * 更新用户信息
     */
    fun updateInfo( username: String=currentUsername,birthday:String , nickname: String="第一次来到Clover") {
        AVUser.getCurrentUser().put("username", username)
        AVUser.getCurrentUser().put("birthday", birthday)
        AVUser.getCurrentUser().put("nickName", nickname)
        AVUser.getCurrentUser().saveInBackground(object : SaveCallback() {
            override fun done(e: AVException?) {
                if (e == null) {
                    view.updateSuccess()
                }else view.updateFailed()
            }
        })
    }

}