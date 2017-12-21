package cn.yoogr.clover.mvp.model.data


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
data class PhotoItemBean(
        val title: String?,
        val objectId: String?,
        val imageUrl: String?,
        val status: Int?,
        val author: String?,
        val avatarUrl: String?,
        val authorId: String?
)