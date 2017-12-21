package cn.yoogr.clover.mvp.model.data

/**
 * Created by yuequan on 2017/11/29.
 *
 * versionCode app版本号
 * version 外部版本
 * message 更新信息
 * iconUrl  图标地址
 * apkUrl  apk安装包地址
 * createTime 创建时间
 * force 是否强制更新  默认0不强制 1强制更新
 * size  安装包大小
 */
data class VersionBean (val versionCode:Number?,
                        val apkName:String?,
                        val version:String?,
                        val message:String?,
                        val iconUrl:String?,
                        val apkUrl:String?,
                        val createTime:String?,
                        val force:Int?,
                        val size:Int?
                     )