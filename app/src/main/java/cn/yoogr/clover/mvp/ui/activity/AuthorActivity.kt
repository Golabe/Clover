package cn.yoogr.clover.mvp.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import cn.yoogr.clover.R
import cn.yoogr.clover.adapter.AuthorPagerAdapter
import cn.yoogr.clover.base.BaseActivity
import cn.yoogr.clover.mvp.model.data.AuthorInfoBean
import cn.yoogr.clover.mvp.presenter.AuthorPresenter
import cn.yoogr.clover.mvp.ui.fragment.AuthorContentFragment
import cn.yoogr.clover.mvp.view.IAuthorView
import cn.yoogr.clover.util.ImageLoaderUtil
import com.bumptech.glide.Priority
import com.lzx.nickphoto.utils.CommonUtil
import kotlinx.android.synthetic.main.activity_author.*

class AuthorActivity : BaseActivity<AuthorPresenter>(), IAuthorView {
    /**
     * 取消关注成功回调
     */
    override fun cancelFollowSuccess() {

    }

    /**
     * 取下关注失败回调
     */
    override fun cancelFollowFailed(code: Int) {
    }

    /**
     * 关注失败回调
     */
    override fun followFailed(code: Int) {
        CommonUtil.showSnackBar(tvFollow, "CANCEL FOLLOW")
    }

    /**
     * 计数失败回调
     */
    override fun onCountFailed(code: Int) {

    }

    override fun followSuccess() {
        CommonUtil.showSnackBar(tvFollow, "FOLLOW SUCCESS")
    }

    /***
     * 关注中
     */
    override fun following() {
        tvFollow.apply {
            text = "+FOLLOW"
            setBackgroundResource(R.drawable.bg_follow_normal)
            //mPresenter.follow(mAuthorId)
        }

    }

    /**
     * 取消关注
     */
    override fun cancelFollow() {
        tvFollow.apply {
            text = "FOLLOWING"
            setBackgroundResource(R.drawable.bg_follow_checked)
          //  mPresenter.cancelFollow(mAuthorId)
        }
    }


    /**
     * 获取作者信息成功回调
     */
    override fun getAuthorInfoSuccess(data: AuthorInfoBean) {
        ImageLoaderUtil.loadImage(this@AuthorActivity, data.avatarUrl, Priority.NORMAL, cvAvatar)
        tvAuthor.text = data.username
        tvNickname.text = data.nickname
        tvFollowers.text = data.follow.toString()
        tvFollowing.text = data.following.toString()
    }

    /**
     * 回去作者信息失败回调
     */
    override fun getAuthorInfoFailed(code: Int) {
    }

    private lateinit var mAuthorId: String
    override fun getLayoutResId(): Int = R.layout.activity_author
    override fun createPresenter(): AuthorPresenter = AuthorPresenter(this)
    private val mFragments = ArrayList<Fragment>()
    override fun init() {
        val bundle = intent.extras
        mAuthorId = bundle.getString("authorId")
        mPresenter.getAuthorInfo(mAuthorId)
        initFragments()

        btnBack.apply {
            setOnClickListener {
                finishAfterTransition()
            }
        }
        mPresenter.getFollowStatus(mAuthorId)
        tvFollow.apply {
            setOnClickListener {
                if (mPresenter.getFollowStatus(mAuthorId)) {
                    mPresenter.follow(mAuthorId)
                }else mPresenter.cancelFollow(mAuthorId)
            }
        }
    }

    /**
     * 初始化Fragments
     */
    private fun initFragments() {
        for (i in 0..2) {
            val fragment = AuthorContentFragment()
            val bundle = Bundle()
            bundle.putInt("index", i)
            fragment.arguments = bundle
            mFragments.add(fragment)
        }
        viewPager.adapter = AuthorPagerAdapter(supportFragmentManager, mFragments)
        tabLayout.setupWithViewPager(viewPager)
    }

}
