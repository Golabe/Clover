package cn.yoogr.clover.util

import android.content.Context

/**
 * Created by yuequan on 2017/11/29.
 *
 * APP版本信息
 */
object VersionInfoUtil {


    /**
     * 获取当前app版本名称
     */
     fun getVersionName(context: Context):String {
        //获取PackagerManager 实例
        val packageManager = context.packageManager
        val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
        return packageInfo.versionName
    }

    /**
     * 获取当前app版本号
     */
    fun  getVersionCode(context: Context):Int{
        val packageManager = context.packageManager
        val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
        return packageInfo.versionCode
    }

}