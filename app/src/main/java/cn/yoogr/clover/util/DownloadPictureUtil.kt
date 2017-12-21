package cn.yoogr.clover.util

import android.content.Context
import android.os.Environment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import java.io.File
import java.io.FileOutputStream

/**
 * Created by yuequan on 2017/12/3.
 *
 * 保存图片工具类
 */
object DownloadPictureUtil {

    /**
     * 保存图片
     */
    fun savePicture(context: Context, url: String, fileName: String, listener: SavePictureListener) {
        Glide.with(context).load(url).asBitmap().toBytes()
                .into(object : SimpleTarget<ByteArray>() {
                    override fun onResourceReady(resource: ByteArray?, glideAnimation: GlideAnimation<in ByteArray>?) {
                        try {
                            saveFileToSD(fileName, resource, listener)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                })
    }

    /**
     * 保存图片到SD卡
     */
    private fun saveFileToSD(fileName: String, resource: ByteArray?, listener: SavePictureListener) {


        //如果手机又sd卡并且有读写权限
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val filePath = Environment.getExternalStorageDirectory().canonicalPath + "/Clover/image"
            val dirl = File(filePath)
            //如果文件夹不存在 创建文件夹
            if (!dirl.exists()) {
                dirl.mkdirs()
            }
            var fileName1 = filePath + "/" + fileName

            //判断文件是否存在
            if (!fileIsExists(fileName1)) {
                val outputStream = FileOutputStream(fileName1)
                //将Byte写入到输出流中
                outputStream.write(resource)
                outputStream.close()
                listener.onSuccess()
            } else listener.onSaved()

        } else listener.onFailed()
    }

    /**
     * 判断文件是否存在
     */
    private fun fileIsExists(fileName: String): Boolean {

        try {
            val file = File(fileName)
            return file.exists()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    /**
     * 保存状态监听
     */
    interface SavePictureListener {
        //保存成功
        fun onSuccess()

        //保存失败
        fun onFailed()

        //已经保存
        fun onSaved()
    }

}
