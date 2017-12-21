package cn.yoogr.clover.mvp.ui.activity

import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import cn.yoogr.clover.R
import cn.yoogr.clover.base.BaseActivity
import cn.yoogr.clover.mvp.presenter.UploadVideoPresenter
import cn.yoogr.clover.mvp.view.IUploadVideoView
import kotlinx.android.synthetic.main.activity_upload_video.*
import org.jetbrains.anko.backgroundResource


class UploadVideoActivity : BaseActivity<UploadVideoPresenter>(), IUploadVideoView {


    override fun updateVideoTabFailed(code: Int) {

    }

    override fun updateVideoTabSuccess() {
        btnPush.text = "SUCCESS"
        finish()
    }

    /**
     * 上传进度监听
     */
    override fun uploadProgress(integer: Int?) {
        pbProgressBar.progress = integer!!
    }

    /**
     * 上传视频失败回调
     */
    override fun uploadFailed(code: Int) {
    }

    private val mTextChangeListener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            val length = edInput.text.length
            tvLength.text = "$length/50"
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }

    override fun getLayoutResId(): Int = R.layout.activity_upload_video

    override fun createPresenter(): UploadVideoPresenter = UploadVideoPresenter(this)

    private lateinit var mVideoUri: Uri
    private lateinit var mVideoPath: String
    override fun init() {
        val bundle = intent.extras
        mVideoUri = bundle.get("videoUri") as Uri
        mVideoPath = bundle.getString("videoPath")

        vvVideoView.apply {
            setVideoURI(mVideoUri)
            start()
            setOnCompletionListener {
                start()
            }
        }
        edInput.apply {
            addTextChangedListener(mTextChangeListener)
        }
        btnBack.setOnClickListener { finish() }
        btnPush.apply {
            setOnClickListener {
                mPresenter.uploadVideo(mVideoPath, edInput.text.trim().toString())
                text = "PUSHING"
                isEnabled = false
                backgroundResource = R.drawable.bg_push_button_checked
            }

        }
    }


    override fun onResume() {
        super.onResume()
        vvVideoView.resume()
    }

    override fun onPause() {
        super.onPause()
        vvVideoView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        vvVideoView.stopPlayback()

    }

    override fun onRestart() {
        super.onRestart()
        vvVideoView.start()
    }
}
