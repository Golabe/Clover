package cn.yoogr.clover.app
import android.app.Application
import com.avos.avoscloud.AVOSCloud

class CloverApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "NLQCW2TINrFGilzy5NCIXkEd-gzGzoHsz", "DCaKXWns7CQp88HGuKGF7zBP")
        //  放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true)





    }


}