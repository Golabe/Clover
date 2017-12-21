package cn.yoogr.clover.widget

import android.support.v4.app.DialogFragment
import cn.yoogr.clover.R
import cn.yoogr.clover.base.BaseDialogFragment
import com.lzx.nickphoto.utils.CommonUtil

/**
 * Created by yuequan on 2017/11/25.
 *
 * Loading  Dialog
 */
class LoadingDialog : BaseDialogFragment() {
    override fun getLayoutResId(): Int = R.layout.dialog_loading

    override fun setDialogStyle(): Int=DialogFragment.STYLE_NO_TITLE

    override fun setDialogTheme(): Int =R.style.Base_ThemeOverlay_AppCompat_Dialog_Alert

    override fun onResume() {
        super.onResume()
        dialog.window.setLayout(CommonUtil.dip2px(activity,150f),CommonUtil.dip2px(activity,150f))
    }
}