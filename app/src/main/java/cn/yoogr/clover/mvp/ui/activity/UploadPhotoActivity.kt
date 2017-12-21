package cn.yoogr.clover.mvp.ui.activity

import android.graphics.BitmapFactory
import android.text.Editable
import android.text.TextWatcher
import cn.yoogr.clover.R
import cn.yoogr.clover.base.BaseActivity
import cn.yoogr.clover.mvp.presenter.UploadPhotoPresenter
import cn.yoogr.clover.mvp.view.IUploadPhotoView
import java.io.FileInputStream
import java.io.FileNotFoundException
import kotlinx.android.synthetic.main.activity_upload_photo.*
import org.jetbrains.anko.backgroundResource


class UploadPhotoActivity : BaseActivity<UploadPhotoPresenter>(), IUploadPhotoView {

    /**
     * 图片上传成功后更新Photo表成功回调
     */
    override fun updatePhotoTabSuccess() {
        btnPush.text = "SUCCESS"
        finish()
    }

    /**
     * 图片上传成功后更新Photo表失败回调
     */
    override fun updatePhotoTabFailed(code: Int) {
    }

    /**
     * 上传图片成功回调
     */
    override fun uploadSuccess() {
    }

    /**
     * 上传图片失败回调
     */
    override fun uploadFailed(code: Int) {
    }

    /**
     * 上传图片进度回调
     */
    override fun uploadProgress(progress: Int?) {
        pbProgressBar.progress = progress!!

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

    private lateinit var mPhotoPath: String
    override fun getLayoutResId(): Int = R.layout.activity_upload_photo

    override fun createPresenter(): UploadPhotoPresenter = UploadPhotoPresenter(this)


    override fun init() {
        val bundle = intent.extras
        mPhotoPath = bundle.getString("photoPath")
        showImage()
        edInput.apply {
            addTextChangedListener(mTextChangeListener)
        }
        btnBack.setOnClickListener { finish() }

        btnPush.apply {
            setOnClickListener {
                mPresenter.uploadPhoto( mPhotoPath, edInput.text.trim().toString())
                text = "PUSHING"
                isEnabled = false
                backgroundResource = R.drawable.bg_push_button_checked
            }

        }
    }

    /**
     * 显示图片
     */
    private fun showImage() {
        var inputStream: FileInputStream? = null
        try {
            inputStream = FileInputStream(mPhotoPath)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            ivImage.setImageBitmap(bitmap)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()

        }
    }


}
