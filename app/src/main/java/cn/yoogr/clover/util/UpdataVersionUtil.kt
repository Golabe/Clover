package cn.yoogr.clover.util

import com.avos.avoscloud.AVQuery
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.GetCallback
import org.jetbrains.anko.toast


/**
 * Created by yuequan on 2017/11/29.
 */
class UpdataVersionUtil {
    companion object {
        val  INSTANCE=UpdataVersionUtil()
    }

    fun getServerVersionInfo(){
        val query = AVQuery<AVObject>("AppVersion")
        query.whereEqualTo("status", 0)
        query.getFirstInBackground(object : GetCallback<AVObject>(){
            override fun done(p0: AVObject?, p1: AVException?) {
                val number = p0?.getString("iconUrl")

            }
        })
    }
}