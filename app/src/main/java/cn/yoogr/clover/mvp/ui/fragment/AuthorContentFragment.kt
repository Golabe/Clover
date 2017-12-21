package cn.yoogr.clover.mvp.ui.fragment

import cn.yoogr.clover.R
import cn.yoogr.clover.base.BaseFragment
import cn.yoogr.clover.mvp.presenter.AuthorContentPresenter
import cn.yoogr.clover.mvp.view.IAuthorContentView

/**
 * Created by yuequan on 2017/11/25.
 */
class AuthorContentFragment : BaseFragment<AuthorContentPresenter>(),IAuthorContentView {
    override fun createPresenter(): AuthorContentPresenter =AuthorContentPresenter(this)


    override fun getLayoutResId(): Int = R.layout.fragment_author_content
}