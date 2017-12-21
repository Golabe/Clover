package cn.yoogr.clover.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cn.yoogr.clover.mvp.model.data.CloverItemBean
import cn.yoogr.clover.widget.CloverListItemView
import cn.yoogr.clover.widget.LoadMoreView

/**
 * Created by yuequan on 2017/12/7.
 */
class CloverListAdapter(val list:MutableList<CloverItemBean>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {

        val TYPE_LOAD_MORE=1
        val TYPE_NORMAL=2
    }

    override fun getItemViewType(position: Int): Int {
        if (position== list.size){
            return TYPE_LOAD_MORE
        }else return TYPE_NORMAL
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_LOAD_MORE) {
            CloverViewHolder(LoadMoreView(parent?.context))
        } else
            CloverViewHolder(CloverListItemView(parent?.context))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (position==list.size)return
        val cloverViewHolder = holder?.itemView as CloverListItemView
        cloverViewHolder.bindView(list[position])

    }

    override fun getItemCount(): Int =list.size+1

    class CloverViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}