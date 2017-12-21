package cn.yoogr.clover.widget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import cn.yoogr.clover.R
import cn.yoogr.clover.mvp.model.data.CloverItemBean
import cn.yoogr.clover.mvp.ui.activity.AuthorActivity
import cn.yoogr.clover.mvp.ui.activity.CloverDetailsActivity
import cn.yoogr.clover.util.ActivityJump
import cn.yoogr.clover.util.ImageLoaderUtil
import cn.yoogr.clover.util.QiNiuUtil
import com.bumptech.glide.Priority
import kotlinx.android.synthetic.main.item_clover_view.view.*
import org.jetbrains.anko.startActivity

/**
 * Created by yuequan on 2017/12/7.
 */
class CloverListItemView(context: Context?, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    init {

        View.inflate(context, R.layout.item_clover_view, this)
    }

    fun bindView(data: CloverItemBean) {
        ImageLoaderUtil.loadImage(context, data.avatarUrl, Priority.NORMAL,cvAvatar)
        ImageLoaderUtil.loadImage(context,QiNiuUtil.imageZip(data.coverUrl),Priority.HIGH,ivImage)
        tvName.text=data.author
        tvTitle.text=data.title
        cvAvatar.setOnClickListener {
            val intent = Intent(context, AuthorActivity::class.java)
            val bundle = Bundle()
            bundle.putString("authorId",data.authorId)
            intent.putExtras(bundle)
            ActivityJump.transitionAnimationJump(intent,context,cvAvatar,"PHOTO")
        }
        ivImage.setOnClickListener {
            context.startActivity<CloverDetailsActivity>("cloverId" to data.cloverId)
        }

    }
}