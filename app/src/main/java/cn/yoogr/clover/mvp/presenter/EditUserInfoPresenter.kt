package cn.yoogr.clover.mvp.presenter

import android.os.Environment
import cn.yoogr.clover.base.BasePresenter
import cn.yoogr.clover.mvp.view.IEditUserInfoView
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVFile
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.SaveCallback

/**
 * Created by yuequan on 2017/12/9.
 */
class EditUserInfoPresenter(val view: IEditUserInfoView) : BasePresenter() {

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
     * 上传用户信息
     */
    fun uploadInfo(username:String,birthday:String,nickname:String){
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