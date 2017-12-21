package cn.yoogr.clover.mvp.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.view.KeyEvent
import android.view.View
import cn.yoogr.clover.R
import kotlinx.android.synthetic.main.activity_love.*

class LoveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_love)
        ViewCompat.animate(ivLove).alpha(1.0f).scaleY(1.0f).scaleX(1.0f).setListener(object :ViewPropertyAnimatorListener{
            override fun onAnimationEnd(view: View?) {
                finish()
            }

            override fun onAnimationCancel(view: View?) {
            }

            override fun onAnimationStart(view: View?) {

            }
        }).duration=500
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode==KeyEvent.KEYCODE_BACK&&event?.repeatCount==0){
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }


}
