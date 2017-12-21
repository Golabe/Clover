package cn.yoogr.clover.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Created by yuequan on 2017/12/1.
 * 图片加载类
 *
 */
object ImageLoaderUtil {

    /**
     * 加载图片
     * url 图片地址
     * defaultImage 默认占位图
     * priority 优先级
     * imageView
     */
    fun loadImage(context: Context, url: String?, priority: Priority, imageView: ImageView) {
        Glide.with(context).load(url)
                .thumbnail(0.8f)
                .priority(priority)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)


    }

}