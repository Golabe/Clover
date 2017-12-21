package cn.yoogr.clover.mvp.model.data

import java.util.*

/**
 * Created by yuequan on 2017/12/6.
 */
data class VideoDetailsBean(
        val createdAt:Date?,
        val place: String?,
        val type: String?,
        val like: Number?,
        val size: Number?,
        val title: String?,
        val author: String?,
        val authorId: String?,
        val avatarUrl: String?,
        val videoUrl: String?,
        val watch: Number?,
        val download:Number?,
        val videoName:String?

)