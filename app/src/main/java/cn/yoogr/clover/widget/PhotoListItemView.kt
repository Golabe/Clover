package cn.yoogr.clover.widget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import cn.yoogr.clover.R
import cn.yoogr.clover.mvp.model.data.PhotoItemBean
import kotlinx.android.synthetic.main.item_photo_view.view.*
import cn.yoogr.clover.mvp.ui.activity.AuthorActivity
import cn.yoogr.clover.mvp.ui.activity.PhotoDetailsActivity
import cn.yoogr.clover.util.ActivityJump
import cn.yoogr.clover.util.ImageLoaderUtil
import cn.yoogr.clover.util.QiNiuUtil
import com.bumptech.glide.Priority
import org.jetbrains.anko.startActivity


/**
 * Created by yuequan on 2017/12/1.
 */
class PhotoListItemView(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.item_photo_view, this)
    }

    fun bindView(data: PhotoItemBean) {

        ImageLoaderUtil.loadImage(context, QiNiuUtil.imageZip(data.imageUrl), Priority.HIGH, image)
        ImageLoaderUtil.loadImage(context, QiNiuUtil.imageZip(data.avatarUrl), Priority.NORMAL, avatar)
        author.text = data.author
        title.text=data.title
        avatar.setOnClickListener {
            val intent = Intent(context, AuthorActivity::class.java)
            val bundle = Bundle()
            bundle.putString("authorId",data.authorId)
            intent.putExtras(bundle)
            ActivityJump.transitionAnimationJump(intent,context,avatar,"PHOTO")
        }
        image.setOnClickListener {

            context.startActivity<PhotoDetailsActivity>("photoId" to data.objectId)
        }
    }
}