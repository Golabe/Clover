package cn.yoogr.clover.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cn.yoogr.clover.mvp.model.data.PhotoItemBean
import cn.yoogr.clover.mvp.ui.activity.PhotoDetailsActivity
import cn.yoogr.clover.widget.LoadMoreView
import cn.yoogr.clover.widget.PhotoListItemView
import org.jetbrains.anko.startActivity
import cn.yoogr.clover.mvp.ui.activity.MainActivity
import android.support.v4.app.ActivityOptionsCompat
import cn.yoogr.clover.R


/**
 * Created by yuequan on 2017/12/1.
 *
 * 图片列表Adapter
 */
class PhotoListAdapter(val context: Context, private val list: MutableList<PhotoItemBean>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val TYPE_LOAD_MORE = 1
        val TYPE_NORMAL = 2

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_LOAD_MORE) {
            return PhotoViewHolder(LoadMoreView(parent?.context))
        } else {
            return PhotoViewHolder(PhotoListItemView(parent?.context))
        }
    }
    override fun getItemCount(): Int = list.size+1
    /**
     * 判断Item类型
     */
    override fun getItemViewType(position: Int): Int {
        return if (position == list.size) {
            TYPE_LOAD_MORE
        } else {
            TYPE_NORMAL
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        //如果是最后一条 不刷新
        if (position == list.size) return
        val photoListItemView = holder?.itemView as PhotoListItemView
        photoListItemView.bindView(list[position])

    }

    class PhotoViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}
