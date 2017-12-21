package cn.yoogr.clover.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cn.yoogr.clover.mvp.model.data.SearchVideoBean
import cn.yoogr.clover.widget.SearchVideoListItemView

/**
 * Created by yuequan on 2017/12/8.
 */
class SearchVideoAdapter(val  list:MutableList<SearchVideoBean>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int =list.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val searchListItemView = holder?.itemView as SearchVideoListItemView
        searchListItemView.bindView(list[position])
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder =SearchVideoViewHolder(SearchVideoListItemView(parent?.context))
    class  SearchVideoViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}