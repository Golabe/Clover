package cn.yoogr.clover.base

import android.os.Handler
import android.os.Looper


/**
 * Created by yuequan on 2017/11/24.
 */
open class BasePresenter {

    companion object {
        val handler by lazy { Handler(Looper.getMainLooper()) }
    }
    //切换到UI线程
    fun uiThread(f: () -> Unit) {
        handler.post { f() }
    }

}