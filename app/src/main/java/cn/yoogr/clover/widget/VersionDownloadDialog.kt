package cn.yoogr.clover.widget

import android.app.DialogFragment
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.TextView
import cn.yoogr.clover.R
import cn.yoogr.clover.base.BaseDialogFragment
import cn.yoogr.clover.mvp.model.data.VersionBean
import com.avos.avoscloud.AVFile
import com.avos.avoscloud.ProgressCallback
import com.avos.avoscloud.AVException
import com.avos.avoscloud.GetDataCallback
import kotlinx.android.synthetic.main.dialog_download_version.*
import java.io.File
import java.io.FileOutputStream


/**
 * Created by yuequan on 2017/11/30.
 */
class VersionDownloadDialog : BaseDialogFragment() {

    var mWave: WaveView? = null
    var mBgDownload: TextView? = null
    var mProgress = 0
    var mApkUrl: String? = null
    var mApkName: String? = null

    override fun getLayoutResId(): Int = R.layout.dialog_download_version
    override fun setDialogStyle(): Int = DialogFragment.STYLE_NO_FRAME
    override fun setDialogTheme(): Int = R.style.Base_ThemeOverlay_AppCompat_Dialog_Alert
    override fun init() {
        isCancelable = false
        mWave = findView(R.id.waveView) as WaveView

        mBgDownload = findView(R.id.tvCancel) as TextView
        mBgDownload!!.setOnClickListener {
            dismiss()
        }
        download()
    }

    fun setDownloadInfo(result: VersionBean) {
        this.mApkUrl = result.apkUrl
        this.mApkName = result.apkName
    }

    /**
     * 下载Apk
     */
    private fun download() {
        val file = AVFile(mApkName, mApkUrl, HashMap())
        file.getDataInBackground(object : GetDataCallback() {
            override fun done(bytes: ByteArray?, e: AVException?) {
                // bytes 就是文件的数据流
                if (e == null && bytes != null) {
                    saveSD(mApkName, bytes)
                }
            }
        }, object : ProgressCallback() {
            override fun done(integer: Int?) {
                // 下载进度数据，integer 介于 0 和 100。
                waveView.setProgress(integer)
            }
        })
    }

    private fun saveSD(fileName: String?, byteArray: ByteArray?) {

        try {
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                val filePath = Environment.getExternalStorageDirectory().canonicalPath + "/Clover/download"
                val file = File(filePath)
                if (!file.exists()) {
                    file.mkdirs()
                }
                val fileName1 = filePath + "/" + fileName

                val outputStream = FileOutputStream(fileName1)
                outputStream.write(byteArray)
                outputStream.close()
                installApk(fileName1)
            }
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }

    /**
     * 启动应用程序安装器
     */
    private fun installApk(fileName1: String) {
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        intent.addCategory("android.intent.category.DEFAULT")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setDataAndType(Uri.parse(fileName1), "application/vnd.android.package-archive")
        startActivity(intent)
    }
}