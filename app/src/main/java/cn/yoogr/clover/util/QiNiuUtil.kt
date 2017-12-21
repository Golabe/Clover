package cn.yoogr.clover.util

/**
 * Created by yuequan on 2017/12/7.
 *
 *七牛云处理工具
 */
object QiNiuUtil {

    /**
     * 通过七牛云压缩图片
     */
    fun imageZip(url:String?):String="$url?imageslim"

    /**
     * 获取视频第一帧
     */
    fun getFirstFrame(url:String?):String="$url?vframe/png/offset/0"


    /**
     * 返回指定图片大小
     */
    fun assignImageSize(url:String?,w:Int,h:Int):String="$url?imageView5/0/w/$w/h/$h"

    /**
     * 高度指定不变 ，宽度等比例缩放
     */
    fun hInvariant(url:String?,w:Int,h:Int):String="$url?imageView2/5/h/$h/w/$w"

    /**
     * 图片模糊处理
     */
    fun imageBluring(url:String?,radius:Int,value:Int)="$url?imageMogr2/blur/"+radius+"x"+value



}