package cn.yoogr.clover.widget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import cn.yoogr.clover.R
import cn.yoogr.clover.mvp.model.data.SearchVideoBean
import cn.yoogr.clover.mvp.ui.activity.AuthorActivity
import cn.yoogr.clover.mvp.ui.activity.MovieDetailsActivity
import cn.yoogr.clover.util.ActivityJump
import cn.yoogr.clover.util.ImageLoaderUtil
import cn.yoogr.clover.util.QiNiuUtil
import com.bumptech.glide.Priority
import kotlinx.android.synthetic.main.item_video_view.view.*
import org.jetbrains.anko.startActivity

/**
 * Created by yuequan on 2017/12/8.
 */
class SearchVideoListItemView(context: Context?) : LinearLayout(context) {

    init {
        View.inflate(context, R.layout.item_video_view, this)
    }

    fun bindView(data: SearchVideoBean) {
        ImageLoaderUtil.loadImage(context, QiNiuUtil.getFirstFrame(data.videoUrl), Priority.NORMAL, ivImage)
        ImageLoaderUtil.loadImage(context, data.avatarUrl, Priority.NORMAL, cvAvatar)
        tvTitle.text = data.title
        tvType.text = data.type

        cvAvatar.setOnClickListener {
            val intent = Intent(context, AuthorActivity::class.java)
            val bundle = Bundle()
            bundle.putString("authorId", data.authorId)
            intent.putExtras(bundle)
            ActivityJump.transitionAnimationJump(intent, context, cvAvatar, "PHOTO")
        }
        ivImage.setOnClickListener { context.startActivity<MovieDetailsActivity>("objectId" to data.objectId, "videoUrl" to data.videoUrl) }

    }

}