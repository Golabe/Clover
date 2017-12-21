package cn.yoogr.clover.mvp.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.yoogr.clover.R
import cn.yoogr.clover.adapter.CloverListAdapter
import cn.yoogr.clover.base.BaseFragment
import cn.yoogr.clover.mvp.presenter.CloverPresenter
import cn.yoogr.clover.mvp.view.ICloverView

/**
 * Created by yuequan on 2017/11/24.
 */
class CloverFragment : BaseFragment<CloverPresenter>(), ICloverView {
    override fun onSuccess() {
        mRefreshLayout.isRefreshing = false
        mRecyclerView.adapter.notifyDataSetChanged()
    }

    override fun isEmpty() {
        mRefreshLayout.isRefreshing = false
        mRecyclerView.adapter.notifyDataSetChanged()
//        toast("Nothing  More Item ")
    }

    override fun onFailed(code: Int) {
        mRefreshLayout.isRefreshing = false
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

    private var isLoadMore=false
    private val skip = 0
    private val size = 5
    lateinit var mRecyclerView: RecyclerView
    lateinit var mRefreshLayout: SwipeRefreshLayout
    override fun createPresenter(): CloverPresenter = CloverPresenter(this)
    override fun getLayoutResId(): Int = R.layout.fragment_clover
    override fun init() {

        mRefreshLayout = activity.findViewById(R.id.refreshLayout)
        mRecyclerView = activity.findViewById(R.id.recyclerView)
        mPresenter.getCloverListData(size, 0)
        mRefreshLayout.apply {
            setColorSchemeColors(resources.getColor(R.color.home_bottom_checked))
            isRefreshing = true
            setOnRefreshListener {
                mPresenter.cloverItems.clear()
                mPresenter.getCloverListData(size, 0)
            }

        }

        mRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = CloverListAdapter(mPresenter.cloverItems)
            addOnScrollListener(mScrollListener)
        }

    }


    private fun loadMore() {
        mPresenter.getCloverListData(size, skip + size)
        mRecyclerView.adapter.notifyDataSetChanged()
    }
}
