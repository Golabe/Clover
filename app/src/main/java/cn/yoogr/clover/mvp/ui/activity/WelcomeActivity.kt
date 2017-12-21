package cn.yoogr.clover.mvp.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.view.View
import cn.yoogr.clover.R
import cn.yoogr.clover.app.CloverConstant
import com.white.easysp.EasySP
import kotlinx.android.synthetic.main.activity_welcome.*
import org.jetbrains.anko.startActivity

class WelcomeActivity : AppCompatActivity() {

    companion object {
        val DURATION=2000L//持续时间
    }
    private val animListener = object : ViewPropertyAnimatorListener {
        override fun onAnimationEnd(view: View?) {
            //判断是否第一次启动 ，如果是 就启动GuideActivity否侧启动MainActivity
            if (EasySP.init(this@WelcomeActivity).getBoolean(CloverConstant.IS_FIRST_LAUNCHER)){
                startActivity<MainActivity>()
            }else startActivity<GuideActivity>()
            finish()
        }

        override fun onAnimationCancel(view: View?) {
        }

        override fun onAnimationStart(view: View?) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        ViewCompat.animate(welcome).scaleY(1.0f).scaleX(1.0f).alphaBy(0.8f).setListener(animListener).duration = DURATION

    }
}
