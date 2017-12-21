package cn.yoogr.clover.mvp.ui.fragment

import android.content.Context
import android.view.WindowManager
import cn.yoogr.clover.R
import cn.yoogr.clover.base.BaseFragment
import cn.yoogr.clover.mvp.presenter.GuidePresenter
import kotlinx.android.synthetic.main.fragment_guide.*

/**
 * Created by yuequan on 2017/11/24.
 */
class GuideFragment : BaseFragment<GuidePresenter>() {
    override fun createPresenter(): GuidePresenter =GuidePresenter()

    companion object {
        val  GUIDE_IMAGE= intArrayOf(R.drawable.bg_guide_movie,R.drawable.bg_guide_clover,R.drawable.bg_guide_photo)
        val  GUIDE_TITLE= intArrayOf(R.string.guide_title1,R.string.guide_title2,R.string.guide_title3)
        val  GUIDE_CONTENT= intArrayOf(R.string.guide_content1,R.string.guide_content2,R.string.guide_content3)

    }
    override fun getLayoutResId(): Int = R.layout.fragment_guide

    override fun init() {
        super.init()
        var index=arguments.getInt("index")

        //设置图片
        guideLogo.setImageResource(GUIDE_IMAGE[index ])
        //设置title
        guideTitle.text=getString(GUIDE_TITLE[index])
        //设置Content
        guideContent.text=getString(GUIDE_CONTENT[index])


    }
}