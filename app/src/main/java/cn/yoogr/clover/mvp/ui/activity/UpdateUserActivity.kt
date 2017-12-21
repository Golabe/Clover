package cn.yoogr.clover.mvp.ui.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.provider.MediaStore
import cn.yoogr.clover.R
import cn.yoogr.clover.app.CloverConstant
import cn.yoogr.clover.base.BaseActivity
import cn.yoogr.clover.mvp.presenter.UpdateUserPresenter
import cn.yoogr.clover.mvp.view.IUserView
import cn.yoogr.clover.widget.LoginButtonLayout
import com.lzx.nickphoto.utils.CommonUtil
import kotlinx.android.synthetic.main.activity_user.*
import org.jetbrains.anko.toast
import java.util.*
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.FileNotFoundException


class UpdateUserActivity : BaseActivity<UpdateUserPresenter>(), IUserView {
    private var year: Int? = null
    private var month: Int? = null
    private var day: Int? = null
    private var dpd: DatePickerDialog? = null
    override fun updateSuccess() {
        toast("注册成功")
        finish()
    }

    override fun updateFailed() {
        toast("注册失败")
        register.loadError()
    }
    override fun createPresenter(): UpdateUserPresenter = UpdateUserPresenter(this)
    override fun getLayoutResId(): Int = R.layout.activity_user
    override fun init() {
        initDate()
        register.apply {
            btn.text = getString(R.string.register)
            setMyOnClickListener(object : LoginButtonLayout.MyOnClickListener {
                override fun onClick() {
                    register()
                }
            })
        }
        openDate.apply {
            setOnClickListener {
                createDatePickerDialog()
            }
        }

        cvAvatar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, null)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent, CloverConstant.CHOOSE_PICTURE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CloverConstant.CHOOSE_PICTURE) {
                CommonUtil.startPhotoZoom(this@UpdateUserActivity, data!!.data)
            }else if (CloverConstant.CROP_PICTURE==requestCode){
                    try {
                        cvAvatar.setImageBitmap(BitmapFactory.decodeStream(contentResolver.openInputStream(Uri.parse(CommonUtil.IMAGE_FILE_LOCATION))))
                        mPresenter.uploadAvatar()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                }
        }
    }

    /**
     * 注册
     */
    private fun register() {
        val name = username.text.toString()
        val nick = nickname.text.toString()
        val birthday = date.text.toString()
        mPresenter.updateInfo(name, birthday, nick)
    }

    /**
     * 初始化日期
     */
    private fun initDate() {
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR) //获取Calendar对象中的年
        month = calendar.get(Calendar.MONTH)//获取Calendar对象中的月
        day = calendar.get(Calendar.DAY_OF_MONTH)//获取这个月的第几天
        date.text = "$year-$month-$day"
    }

    /**
     * 创建日期选择器dialog
     */
    private fun createDatePickerDialog() {
        dpd = DatePickerDialog(this@UpdateUserActivity, dateListener, year!!, month!!, day!!)
        dpd!!.show()//显示DatePickerDialog组件
    }

    /**
     * 日期选择器回调
     */
    private val dateListener = DatePickerDialog.OnDateSetListener { p0, y, m, d ->
        year = y
        month = m
        day = d
        updateDate()
    }

    /**
     * 更新textView显示日期
     */
    private fun updateDate() {
        month = month!! + 1
        date.text = "$year-$month-$day"
    }
}
