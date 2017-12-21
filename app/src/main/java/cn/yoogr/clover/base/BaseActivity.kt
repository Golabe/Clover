package cn.yoogr.clover.base

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import org.jetbrains.anko.toast
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.widget.EditText


/**
 * Created by yuequan on 2017/11/24.
 */
abstract class BaseActivity<P : BasePresenter> : AppCompatActivity() {

    private lateinit var mContext: Context
    lateinit var mPresenter: P
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(isStatusBar()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.TRANSPARENT
            }
        }
        setContentView(getLayoutResId())
        mContext = this
        mPresenter = createPresenter()
        init()
    }

    /**
     * 是否开启沉浸式状态栏
     */
    open fun isStatusBar():Boolean=true

    //返回布局ID
    abstract fun getLayoutResId(): Int

    //创建Presenter
    abstract fun createPresenter(): P

    //初始化
    open fun init() {
    }

    open fun myToast(msg: String) {
        runOnUiThread { toast(msg) }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * 隐藏软键盘
     */
    fun hideSoftInput(edit:EditText?){

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(edit?.windowToken, 0)
}

    /**
     * 显示软键盘
     */
    fun showSoftInput(edit:EditText?){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInputFromInputMethod(edit?.windowToken, 0);
    }



}