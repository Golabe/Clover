package cn.yoogr.clover.widget

import android.app.DialogFragment
import android.support.v4.view.ViewCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cn.yoogr.clover.R
import cn.yoogr.clover.adapter.MyViewPropertyAnimatorListenerAdapter
import cn.yoogr.clover.base.BaseDialogFragment
import cn.yoogr.clover.mvp.model.data.VersionBean
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by yuequan on 2017/11/25.
 *
 * 版本更新提示Dialog
 */
class VersionUpdateDialog : BaseDialogFragment() {

    private lateinit var mCancel: ImageView
    private var mDownload: TextView? = null
    private var mVersion: TextView? = null
    private var mIcon: CircleImageView? = null
    private var mMessage: TextView? = null
    private var mSize: TextView? = null
    private var msg: String? = null
    private var v: String? = null
    private var icUrl: String? = null
    private var force: Int? = null
    private var size: Int? = null
    override fun getLayoutResId(): Int = R.layout.dialog_up_version
    override fun setDialogStyle(): Int = DialogFragment.STYLE_NO_FRAME
    override fun setDialogTheme(): Int = R.style.Theme_AppCompat_Dialog_Alert

    //    动画执行监听
    private val animateListener = object : MyViewPropertyAnimatorListenerAdapter() {
        override fun onAnimationEnd(view: View?) {
            dismiss()
        }
    }

    companion object {
        val DURATION = 200L
    }

    override fun init() {
        mCancel = findView(R.id.cancel) as ImageView
        mDownload = findView(R.id.download) as TextView
        mVersion = findView(R.id.version) as TextView
        mIcon = findView(R.id.icon) as CircleImageView
        mMessage = findView(R.id.message) as TextView
        mSize = findView(R.id.size) as TextView
        mCancel.apply {
            setOnClickListener {
                ViewCompat.animate(mCancel).rotationBy(90f).setListener(animateListener).duration = DURATION
            }
        }

        mDownload!!.setOnClickListener {
            if (downloadListener != null) {
                downloadListener.onClick()
            }
        }

        //判断当前版本是否强制更新
        if (force == 1) {
            isCancelable = false
        }
        //如果当前版本强制更新，隐藏取消按钮和设置dialog不可取消
        if (!isCancelable) {
            mCancel.visibility = View.GONE
        }
        mSize?.text = "新版大小："+""+"M"//转换为M
        mVersion?.text = v
        mMessage?.text = msg
        Glide.with(context).load(icUrl).into(mIcon)
    }

    /**
     * 设置版本信息
     */
    fun setUpdateInfo(result: VersionBean) {
        this.v = result.version
        this.msg = result.message
        this.icUrl = result.iconUrl
        this.force = result.force
        this.size = result.size

    }

    private lateinit var downloadListener: OnDownloadClickListener
    fun setDownloadClickListener(listener: OnDownloadClickListener) {
        this.downloadListener = listener
    }

    interface OnDownloadClickListener {
        fun onClick()
    }

}
