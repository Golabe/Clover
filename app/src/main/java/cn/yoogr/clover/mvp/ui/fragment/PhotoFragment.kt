package cn.yoogr.clover.mvp.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.yoogr.clover.R
import cn.yoogr.clover.adapter.PhotoListAdapter
import cn.yoogr.clover.base.BaseFragment
import cn.yoogr.clover.mvp.presenter.PhotoPresenter
import cn.yoogr.clover.mvp.view.IPhotoView

/**
 * Created by yuequan on 2017/11/24.
 */
class PhotoFragment : BaseFragment<PhotoPresenter>(), IPhotoView {

    /**
     * 获取数据为空时调用
     */
    override fun isEmpty() {
        mRefreshLayout.isRefreshing=false
     //   toast("Nothing More Item")

    }

    private val skip = 0
    private val size = 5
    override fun onSuccess() {
        mRefreshLayout.isRefreshing = false
        mRecyclerView.adapter.notifyDataSetChanged()
    }

    override fun onFailed(code: Int) {
        mRefreshLayout.isRefreshing = false
    }

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRefreshLayout: SwipeRefreshLayout

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

    override fun createPresenter(): PhotoPresenter = PhotoPresenter(this)
    override fun getLayoutResId(): Int = R.layout.fragment_photo

    override fun init() {
        mRefreshLayout = activity.findViewById(R.id.refreshLayout)
        mRecyclerView = activity.findViewById(R.id.recyclerView)
        mPresenter.getPhotoListData(size, 0)
        mRefreshLayout.apply {
            setColorSchemeColors(resources.getColor(R.color.home_bottom_checked))
            isRefreshing = true
            setOnRefreshListener {
                mPresenter.mPhotoItems.clear()
                mPresenter.getPhotoListData(size, 0)
            }

        }
        mRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = PhotoListAdapter(context, mPresenter.mPhotoItems)
            addOnScrollListener(mScrollListener)
        }
    }



    /**
     * 加载更多
     */
    fun loadMore() {
        mPresenter.getPhotoListData(size, skip + size)
        mRecyclerView.adapter.notifyDataSetChanged()
    }

}