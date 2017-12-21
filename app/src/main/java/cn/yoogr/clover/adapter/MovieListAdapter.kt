package cn.yoogr.clover.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import cn.yoogr.clover.mvp.model.data.MovieItemBean
import cn.yoogr.clover.widget.LoadMoreView
import cn.yoogr.clover.widget.MovieListItemView

/**
 * Created by yuequan on 2017/12/4.
 */
class MovieListAdapter (val context:Context,val list:MutableList<MovieItemBean>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {

        val  TYPE_LOAD_MORE=1
        val  TYPE_NORMAL=2
    }
    override fun getItemViewType(position: Int): Int {
        return if (position== list.size){
            TYPE_LOAD_MORE
        }else TYPE_NORMAL
    }
    override fun getItemCount(): Int =list.size+1

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType== TYPE_LOAD_MORE){
            MovieViewHolder(LoadMoreView(parent?.context))
        }else MovieViewHolder(MovieListItemView(parent?.context))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (position==list.size)return
        val movieListItemView = holder?.itemView as MovieListItemView
        movieListItemView.bindView(list[position])
    }


    class MovieViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}