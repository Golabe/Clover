package cn.yoogr.clover.mvp.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.yoogr.clover.R
import cn.yoogr.clover.adapter.SearchVideoAdapter
import cn.yoogr.clover.base.BaseFragment
import cn.yoogr.clover.mvp.presenter.SearchVideoPresenter
import cn.yoogr.clover.mvp.view.ISearchContentView
import org.jetbrains.anko.support.v4.toast

/**
 * Created by yuequan on 2017/12/8.
 */
class SearchVideoFragment : BaseFragment<SearchVideoPresenter>(), ISearchContentView {

    override fun searchSuccess() {
        mRefreshLayout.isRefreshing = false
        mRecyclerView.adapter.notifyDataSetChanged()
    }

    override fun searchFailed(code: Int) {
        mRefreshLayout.isRefreshing = false
        toast("FAILED")
    }

    override fun isEmpty() {
        mRefreshLayout.isRefreshing = false

    }

    private var mSearchContent = ""
    lateinit var mRecyclerView: RecyclerView
    lateinit var mRefreshLayout: SwipeRefreshLayout
    override fun getLayoutResId(): Int = R.layout.fragment_search_video

    override fun createPresenter(): SearchVideoPresenter = SearchVideoPresenter(this)
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

    val size = 10
    var skip = size
    override fun init() {

        mRefreshLayout = activity.findViewById(R.id.refreshLayout)
        mRecyclerView = activity.findViewById(R.id.recyclerView)
        mRefreshLayout.apply {
            setColorSchemeColors(resources.getColor(R.color.home_bottom_checked))
            //  isRefreshing = true
            setOnRefreshListener {
                if (mSearchContent.isNotEmpty()) {
                    mPresenter.searchVideoItems.clear()
                    mPresenter.searchVideo(mSearchContent, size, 0)
                }else mRefreshLayout.isRefreshing=false

            }

        }
        mRecyclerView.apply {

            layoutManager = LinearLayoutManager(context)
            adapter = SearchVideoAdapter(mPresenter.searchVideoItems)
            addOnScrollListener(mScrollListener)
        }
    }

    fun search(str: String) {
        mSearchContent = str
        mPresenter.searchVideo(mSearchContent, 10, 0)

    }

    /**
     * 加载更多
     */
    private fun loadMore() {
        mPresenter.searchVideo(mSearchContent, size, size + skip)
        mRecyclerView.adapter.notifyDataSetChanged()
    }
}
