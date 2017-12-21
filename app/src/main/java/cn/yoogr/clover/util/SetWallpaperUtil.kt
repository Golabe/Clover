package cn.yoogr.clover.util

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import cn.yoogr.clover.app.CloverConstant
import cn.yoogr.clover.widget.SetWallpaperDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.white.easysp.EasySP

/**
 * Created by yuequan on 2017/12/1.
 * 设置壁纸工具类
 */
class SetWallpaperUtil {
    lateinit var mWallpaperManager: WallpaperManager
    lateinit var mDialog: SetWallpaperDialog

    companion object {
        val INSTANCE = SetWallpaperUtil()
    }

    /**
     * 设置壁纸
     */
    fun setWallpaper(activity: AppCompatActivity, url: String, objectId: String, listener: SetWallpaperListener) {
        mWallpaperManager = WallpaperManager.getInstance(activity)
        mDialog = SetWallpaperDialog()

        mDialog.show(activity.supportFragmentManager, "set_wallpaper")
        Glide.with(activity).load(url).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                        mWallpaperManager.setBitmap(resource)
                        mDialog.dismiss()
                        //设置壁纸成功 保存图片objectId
                        EasySP.init(activity).putString(CloverConstant.IS_SET_AS_WALLPAPER, objectId)

                        listener?.onSuccess()

                    }

                    override fun onDestroy() {
                        super.onDestroy()
                        mDialog.dismiss()
                        listener?.onFailed()
                    }
                })
    }

    /**
     * 设置壁纸状态监听
     */
    interface SetWallpaperListener {
        fun onSuccess()
        fun onFailed()
    }
}