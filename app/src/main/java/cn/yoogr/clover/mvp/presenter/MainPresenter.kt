package cn.yoogr.clover.mvp.presenter

import android.content.Context
import android.view.View
import cn.yoogr.clover.base.BasePresenter
import cn.yoogr.clover.mvp.model.data.VersionBean
import cn.yoogr.clover.mvp.view.IMainView
import cn.yoogr.clover.util.VersionInfoUtil
import com.avos.avoscloud.*
import com.avos.avoscloud.AVObject
import com.bumptech.glide.Glide
import com.lzx.nickphoto.utils.CommonUtil
import de.hdodenhof.circleimageview.CircleImageView


/**
 * Created by yuequan on 2017/11/26.
 */
class MainPresenter(val view: IMainView) : BasePresenter() {

    fun loginStatus() {
        if (CommonUtil.isLogin()){
            view.loginIn()// 跳转到个人页
        }else view.noLogin()//可打开用户注册界面

    }

    /**
     * 版本更新
     */
    fun getVersionInfo(context: Context) {
        val query = AVQuery<AVObject>("AppVersion")
        query.orderByDescending("id")
        query.limit(1)// 最多返回 1 条结果
        query.getFirstInBackground(object : GetCallback<AVObject>() {
            override fun done(avObject: AVObject?, e: AVException?) {
                if (e == null&&avObject!=null) {
                    val versionCode = avObject?.getInt("versionCode")
                    val avFile = avObject?.getAVFile<AVFile>("apkUrl")
                    val apkName = avFile?.originalName
                    val apkUrl = avFile?.url
                    val size = avFile?.size
                    val versionName = avObject?.getString("version")
                    val iconUrl = avObject?.getAVFile<AVFile>("iconUrl")?.url
                    val message = avObject?.getString("updateMessage")
                    val createDate = avObject?.getString("createdAt")
                    val force = avObject?.getInt("force")
                    if (versionCode!! > VersionInfoUtil.getVersionCode(context)) {
                        uiThread {
                            view.hasVersion(VersionBean(versionCode, apkName, versionName, message, iconUrl, apkUrl, createDate, force, size))
                        }
                    }
                }
            }
        })

    }

    /**
     * 获取用户头像
     */
    fun getAvatarData() {
        if (CommonUtil.isLogin()) {
            val userId = AVUser.getCurrentUser()?.objectId
            val userQuery = AVQuery<AVObject>("_User")
            userQuery.getInBackground(userId, object : GetCallback<AVObject>() {
                override fun done(avUser: AVObject?, e: AVException?) {
                    if (e==null){
                        val url = avUser?.getString("avatarUrl")
                        view.getAvatarSuccess(url)
                    }
                }
            })
        }else view.getAvatarFailed()

    }
}