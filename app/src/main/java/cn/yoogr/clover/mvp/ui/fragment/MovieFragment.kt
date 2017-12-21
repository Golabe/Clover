package cn.yoogr.clover.mvp.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.yoogr.clover.R
import cn.yoogr.clover.adapter.MovieListAdapter
import cn.yoogr.clover.base.BaseFragment
import cn.yoogr.clover.mvp.presenter.MoviePresenter
import cn.yoogr.clover.mvp.view.IMovieView
import org.jetbrains.anko.find

/**
 * Created by yuequan on 2017/11/24.
 */
class MovieFragment : BaseFragment<MoviePresenter>(), IMovieView {

    /**
     * 数据为空回调
     */
    override fun isEmpty() {
        mRefreshLayout.isRefreshing=false
       // toast("Nothing More Item")
    }


    override fun onSuccess() {
        mRecyclerView.adapter.notifyDataSetChanged()
        mRefreshLayout?.isRefreshing = false

    }
    override fun onFailed(code: Int) {
        mRefreshLayout?.isRefreshing = false
    }

    /**
     * RecyclerView 滚动监听
     */
    private val mScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
            val totalItemCount = recyclerView.adapter.itemCount
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            val visibleItemCount = recyclerView.childCount
            //滚动到最后一个
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItemPosition == totalItemCount - 1 && visibleItemCount > 0) {
                loadMore()
            }

        }
    }
    private val skip = 0
    private val size = 6
    lateinit var mRecyclerView: RecyclerView
    lateinit var mRefreshLayout: SwipeRefreshLayout
    override fun createPresenter(): MoviePresenter = MoviePresenter(this)
    override fun getLayoutResId(): Int = R.layout.fragment_movie
    override fun init() {
        mRecyclerView = activity.find(R.id.recyclerView)
        mRefreshLayout = activity.find(R.id.refreshLayout)

        mPresenter.getMovieListData(size, 0)
        mRefreshLayout.apply {
            setColorSchemeColors(resources.getColor(R.color.home_bottom_checked))
            isRefreshing = true
            setOnRefreshListener {
                mPresenter.movieItems.clear()
                mPresenter.getMovieListData(size, 0)
            }
        }

        mRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = MovieListAdapter(context, mPresenter.movieItems)
            addOnScrollListener(mScrollListener)
        }


    }

    /**
     * 加载更多
     */
    fun loadMore() {
        mPresenter.getMovieListData(size, skip + size)
        mRecyclerView.adapter.notifyDataSetChanged()
    }
}