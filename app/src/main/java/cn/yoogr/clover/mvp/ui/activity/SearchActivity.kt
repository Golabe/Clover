package cn.yoogr.clover.mvp.ui.activity

import android.support.v4.app.Fragment
import android.text.Editable
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import cn.yoogr.clover.R
import cn.yoogr.clover.adapter.SearchPagerAdapter
import cn.yoogr.clover.adapter.TextWatcherListenerAdapter
import cn.yoogr.clover.base.BaseActivity
import cn.yoogr.clover.mvp.presenter.SearchPresenter
import cn.yoogr.clover.mvp.ui.fragment.SearchVideoFragment
import cn.yoogr.clover.mvp.view.ISearchView
import cn.yoogr.clover.widget.SearchLayout
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_search_view.*
import kotlinx.android.synthetic.main.layout_search_view.view.*

class SearchActivity : BaseActivity<SearchPresenter>(), ISearchView {


    private val mTextWatchListener = object : TextWatcherListenerAdapter() {
        override fun afterTextChanged(s: Editable?) {
            if (s!!.isNotEmpty()) {
                right?.visibility = View.VISIBLE
            } else right?.visibility = View.GONE

        }
    }

    override fun isStatusBar(): Boolean = false
    override fun getLayoutResId(): Int = R.layout.activity_search
    override fun createPresenter(): SearchPresenter = SearchPresenter(this)
    private var mFragments = ArrayList<Fragment>()

    override fun init() {
        initSearchLayout()
        initFragments()
        viewPager.adapter = SearchPagerAdapter(supportFragmentManager, mFragments)
        tabLayout.setupWithViewPager(viewPager)
    }


    val video by lazy {
        SearchVideoFragment()
    }


    private fun initFragments() {

        mFragments.add(video)

    }

    private fun initSearchLayout() {
        searchLayout.apply {
            right?.setImageResource(R.drawable.ic_searcch)
            left?.setImageResource(R.drawable.ic_back)
            edit?.isFocusable = true
            edit?.isCursorVisible = true
            search.addTextChangedListener(mTextWatchListener)
            setMyOnClickListener(object : SearchLayout.MyOnClickListener {
                override fun onClick(v: View) {
                    when (v.id) {
                        R.id.right -> {
                            hideSoftInput(edit)
                            val searchContent = edit?.text.toString()
                            video.search(searchContent)
                        }

                        R.id.left -> finishAfterTransition()
                    }

                }
            })
            edit?.setOnEditorActionListener(TextView.OnEditorActionListener { p0, actionId, event ->
                if (actionId==EditorInfo.IME_ACTION_SEARCH){
                    hideSoftInput(edit)
                    video.search(edit?.text.toString())
                }
                true })


        }
    }
}
