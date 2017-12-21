package cn.yoogr.clover.mvp.model.data

/**
 * Created by yuequan on 2017/12/4.
 */
data class UserInfoBean(
        val username:String?,
        val nickname:String?,
        val avatarUrl:String?,
        val background:String?,
        val videoTotal:Int?,
        val photoTotal:Int?,
        val cloverTotal:Int?,
        val follow:Int?,
        val birthday:String?
)