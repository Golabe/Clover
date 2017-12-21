package cn.yoogr.clover.mvp.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

import cn.yoogr.clover.R
import cn.yoogr.clover.adapter.GuidePagerAdapter
import cn.yoogr.clover.adapter.MyOnPageChangeListenerAdapter
import cn.yoogr.clover.app.CloverConstant
import cn.yoogr.clover.mvp.ui.fragment.GuideFragment
import com.white.easysp.EasySP
import kotlinx.android.synthetic.main.activity_guide.*
import org.jetbrains.anko.startActivity

class GuideActivity : AppCompatActivity() {
    private val mFragments = ArrayList<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        initFragment()
        guide.adapter = GuidePagerAdapter(supportFragmentManager, mFragments)
        guide.addOnPageChangeListener(object : MyOnPageChangeListenerAdapter() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                initIndicator(position)
                initSkip(position)
                initNext(position)
            }
        })
    }

    private fun initNext(position: Int) {
        if (position == mFragments.size - 1) {
            next.text = getString(R.string.go)
            next.setOnClickListener {
                startActivity<MainActivity>()
                //设置是否是第一次启动
                EasySP.init(this@GuideActivity).putBoolean(CloverConstant.IS_FIRST_LAUNCHER, true)
                finish()
            }
        } else {
            next.text = getString(R.string.next)
            next.setOnClickListener {
                guide.currentItem = position + 1
            }
        }
    }

    private fun initSkip(position: Int) {
        if (position == mFragments.size - 1) {
            skip.visibility = View.GONE
        } else skip.visibility = View.VISIBLE
        skip.setOnClickListener {
            //设置是否是第一次启动
            EasySP.init(this@GuideActivity).putBoolean(CloverConstant.IS_FIRST_LAUNCHER, true)
            startActivity<MainActivity>()
            finish()
        }
    }

    /**
     * 初始化指示器
     */
    private fun initIndicator(index: Int) {
        when (index) {
            0 -> {
                dot_1.setImageResource(R.drawable.dot_focus)
                dot_2.setImageResource(R.drawable.dot_normal)
                dot_3.setImageResource(R.drawable.dot_normal)
            }
            1 -> {
                dot_1.setImageResource(R.drawable.dot_normal)
                dot_2.setImageResource(R.drawable.dot_focus)
                dot_3.setImageResource(R.drawable.dot_normal)
            }
            2 -> {
                dot_1.setImageResource(R.drawable.dot_normal)
                dot_2.setImageResource(R.drawable.dot_normal)
                dot_3.setImageResource(R.drawable.dot_focus)
            }
        }
    }

    /***
     * 创建Fragment
     */
    private fun initFragment() {
        for (i in 0..2) {
            val fragment = GuideFragment()
            val bundle = Bundle()
            bundle.putInt("index", i)
            fragment.arguments = bundle
            mFragments.add(fragment)
        }
    }
}
