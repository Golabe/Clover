package cn.yoogr.clover.widget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import cn.yoogr.clover.R
import cn.yoogr.clover.mvp.model.data.MovieItemBean
import cn.yoogr.clover.mvp.ui.activity.AuthorActivity
import cn.yoogr.clover.mvp.ui.activity.MovieDetailsActivity
import cn.yoogr.clover.util.ActivityJump
import cn.yoogr.clover.util.ImageLoaderUtil
import cn.yoogr.clover.util.QiNiuUtil
import kotlinx.android.synthetic.main.item_video_view.view.*
import com.bumptech.glide.Priority
import org.jetbrains.anko.startActivity

/**
 * Created by yuequan on 2017/12/4.
 */
class MovieListItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {

    init {

        View.inflate(context, R.layout.item_video_view, this)
    }

    fun bindView(data: MovieItemBean) {

        ImageLoaderUtil.loadImage(context,QiNiuUtil.getFirstFrame(data.videoUrl), Priority.HIGH, ivImage)

        ImageLoaderUtil.loadImage(context, QiNiuUtil.imageZip(data.avatarUrl), Priority.NORMAL, cvAvatar)

        tvTitle.text= data.videoTitle
        tvType.text= data.videoType

        cvAvatar.setOnClickListener {

            val intent = Intent(context, AuthorActivity::class.java)
            val bundle = Bundle()
            bundle.putString("authorId",data.authorId)
            intent.putExtras(bundle)
            ActivityJump.transitionAnimationJump(intent,context,cvAvatar,"PHOTO")
        }
        ivImage.setOnClickListener {
            context.startActivity<MovieDetailsActivity>("objectId" to data.objectId,"videoUrl" to data.videoUrl)
        }
    }

}
