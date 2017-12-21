package cn.yoogr.clover.mvp.model.data

import java.util.*

/**
 * Created by yuequan on 2017/12/1.
 * ObjectId  唯一id
 * imageUrl  图片地址
 * color 图片色系
 * likes 点赞数
 * name 图片名称
 * size 图片大小
 * download 下载数
 * type 图片类型
 * status 图片状态 默认0 1删除
 * avatarUrl 作者头像地址
 */
data class PhotoDetailsBean (
        val objectId: String?,
        val imageUrl: String?,
        val color: String?,
        val likes: Int?,
        val imageName: String?,
        val size: Number?,
        val download: Int?,
        val type: String?,
        val avatar:String?,
        val authorId:String?,
        val avatarUrl:String?,
        val createDate:Date?,
        val location:String?

)
