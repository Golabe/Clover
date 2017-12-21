package cn.yoogr.clover.mvp.ui.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import cn.yoogr.clover.R
import cn.yoogr.clover.app.CloverConstant
import cn.yoogr.clover.base.BaseActivity
import cn.yoogr.clover.mvp.presenter.EditUserInfoPresenter
import cn.yoogr.clover.mvp.view.IEditUserInfoView
import cn.yoogr.clover.util.ImageLoaderUtil
import com.bumptech.glide.Priority
import com.lzx.nickphoto.utils.CommonUtil
import kotlinx.android.synthetic.main.activity_edit_user_info.*
import java.util.*

class EditUserInfoActivity : BaseActivity<EditUserInfoPresenter>(), IEditUserInfoView {
    override fun updateSuccess() {
        finish()
    }

    override fun updateFailed() {
    }

    override fun getLayoutResId(): Int = R.layout.activity_edit_user_info
    override fun createPresenter(): EditUserInfoPresenter = EditUserInfoPresenter(this)
    override fun init() {

        val bundle = intent.extras
        val avatarUrl = bundle.getString("avatarUrl")
        val birthday = bundle.getString("birthday")
        val username = bundle.getString("username")
        val nickname = bundle.getString("nickname")

        ImageLoaderUtil.loadImage(this@EditUserInfoActivity, avatarUrl, Priority.NORMAL, cvAvatar)
        tvDate.text = birthday
        ivBack.setOnClickListener { finish() }
        edNickname.setText(nickname)
        edUsername.setText(username)

        cvAvatar.setOnClickListener { startAlbum() }
        ivYes.setOnClickListener {
            val username = edUsername.text.trim().toString()
            val nickname = edNickname.text.trim().toString()
            val birthday = tvDate.text.trim().toString()
            mPresenter.uploadInfo(username, birthday, nickname)
        }

        llOpenDate.setOnClickListener { initDate() }
    }

    private fun initDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this@EditUserInfoActivity, dateListener, year, month, day).show()
    }

    private val dateListener = DatePickerDialog.OnDateSetListener { p0, y, m, d ->

        var m = m + 1
        tvDate.text = "$y -$m -$d"
    }


    /**
     * 跳转相册
     */
    private fun startAlbum() {
        val intent = Intent(Intent.ACTION_PICK, null)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(intent, CloverConstant.CHOOSE_PICTURE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CloverConstant.CHOOSE_PICTURE -> CommonUtil.startPhotoZoom(this@EditUserInfoActivity, data!!.data)
                CloverConstant.CROP_PICTURE -> {
                    try {
                        cvAvatar.setImageBitmap(BitmapFactory.
                                decodeStream(contentResolver.
                                        openInputStream(Uri.parse(CommonUtil.IMAGE_FILE_LOCATION))))
                        mPresenter.uploadAvatar()
                    } catch (e: FileSystemException) {
                        e.printStackTrace()
                    }

                }

            }


        }
    }
}
