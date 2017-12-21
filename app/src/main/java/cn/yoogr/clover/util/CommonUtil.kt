package com.lzx.nickphoto.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.view.View
import com.avos.avoscloud.AVUser
import java.text.SimpleDateFormat
import java.util.*
import android.provider.MediaStore.Images.ImageColumns
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import cn.yoogr.clover.app.CloverConstant.CROP_PICTURE
import java.io.File


/**
 * Created by yuequan on 2017/11/25.
 */
object CommonUtil {


    /**
     * dip 转 px
     */
    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * 显示SnackBar
     */
    fun showSnackBar(view: View, text: String, duration: Int = Snackbar.LENGTH_SHORT) {
        val mSnackBar: Snackbar = Snackbar.make(view, text, duration)
        mSnackBar.view.setBackgroundColor(Color.BLACK)
        mSnackBar.show()
    }

    /**
     * 判断是否登录
     */
    fun isLogin(): Boolean {
        val currentUser = AVUser.getCurrentUser()
        return currentUser != null
    }

    /**
     * 获取问价名
     */
    fun getFileName(path: String): String? {
        val start = path.lastIndexOf("/")
        //val end = path.lastIndexOf(".")
        if (start != -1) {
            return path.substring(start + 1)
        } else return null

    }

    fun getString(s: String, s1: String)//s是需要删除某个子串的字符串s1是需要删除的子串
            : String {
        val postion = s.indexOf(s1)
        val length = s1.length
        val Length = s.length
        return s.substring(0, postion) + s.substring(postion + length, Length)//返回已经删除好的字符串
    }

    /**
     * 获取系统时间
     */
    fun getFilePath(): String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())


    /**
     * 获取路径
     */
    fun getUriPath(context: Context, uri: Uri?): String? {
        var data: String? = null
        if (null == uri)
            return null
        val scheme = uri!!.scheme
        if (scheme == null)
            data = uri!!.path
        else if (ContentResolver.SCHEME_FILE == scheme) {
            data = uri!!.path
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val cursor = context.contentResolver.query(uri, arrayOf(ImageColumns.DATA), null, null, null)
            if (null != cursor) {
                if (cursor!!.moveToFirst()) {
                    val index = cursor!!.getColumnIndex(ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor!!.getString(index)
                    }
                }
                cursor!!.close()
            }
        }
        return data
    }

    /**
     * 图片剪裁工具
     */
    val IMAGE_FILE_LOCATION = "file:///" + Environment.getExternalStorageDirectory().canonicalPath + "/Clover/image/avatar.jpg"
    val IMAGE_URL = Uri.parse(IMAGE_FILE_LOCATION)
    fun startPhotoZoom(context: Activity, uri: Uri) {
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true")
        intent.putExtra("scale", true)
        intent.putExtra("aspectX", 2)
        intent.putExtra("aspectY", 2)
        intent.putExtra("outputX", 200)
        intent.putExtra("outputY", 200)
        intent.putExtra("return-data", false)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, IMAGE_URL)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true) // no face detection

        context.startActivityForResult(intent, CROP_PICTURE)

    }

}
