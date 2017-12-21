package cn.yoogr.clover.base

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * Created by yuequan on 2017/11/25.
 *
 *  弹出窗口基类
 *
 */
abstract class BaseDialogFragment : DialogFragment() {

  private var rootView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(setDialogStyle(), setDialogTheme())
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater?.inflate(getLayoutResId(), container, false)
        init()
        return rootView
    }

    abstract fun getLayoutResId(): Int


    //设置Dialog  样式
    abstract fun setDialogStyle(): Int

    //设置Dialog 主题
    abstract fun setDialogTheme(): Int

    open fun init() {}


    protected fun findView(id: Int): View {
        return rootView!!.findViewById(id)
    }
//    override fun onResume() {
//        super.onResume()
//        // dialog.window.setLayout(CommonUtil.dip2px(activity, setDialogWidth()), CommonUtil.dip2px(activity, setDialogHeight()))
//    }


}
