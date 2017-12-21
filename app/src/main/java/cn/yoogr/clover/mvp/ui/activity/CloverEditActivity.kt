package cn.yoogr.clover.mvp.ui.activity


import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.provider.MediaStore
import cn.yoogr.clover.R
import cn.yoogr.clover.app.CloverConstant.OPEN_ALBUM_REQUEST_CODE
import cn.yoogr.clover.base.BaseActivity
import cn.yoogr.clover.mvp.presenter.CloverEditPresenter
import cn.yoogr.clover.mvp.view.ICloverEditView
import com.lzx.nickphoto.utils.CommonUtil
import jp.wasabeef.richeditor.RichEditor
import kotlinx.android.synthetic.main.activity_clover_edit.*


class CloverEditActivity : BaseActivity<CloverEditPresenter>(), ICloverEditView {
    override fun pushSuccess() {
        CommonUtil.showSnackBar(ivPush, "PUSH SUCCESS")
        finish()
    }

    override fun pushFailed(code: Int) {
        CommonUtil.showSnackBar(ivPush, "PUSH FAILED")
    }

    /**
     * 图片上传成功回调
     */
    override fun uploadImageSuccess(url: String, name: String, objectId: String) {

        editor.insertImage("$url\" style=\"width:100%;", "alt")
        this.mCoverUrl = url
    }

    /**
     *图片上传失败回调
     */
    override fun uploadImageFailed(code: Int) {
    }

    /**
     * 图片上传进度
     */
    override fun uploadImageProgress(integer: Int?) {
        pbProgressBar.progress = integer!!
    }

     var mCoverUrl: String?=null
     var mContent: String?=null
    override fun getLayoutResId(): Int = R.layout.activity_clover_edit
    override fun createPresenter(): CloverEditPresenter = CloverEditPresenter(this)
    override fun init() {


        editor.setEditorHeight(200)
        editor.setEditorFontSize(18)
        editor.setEditorFontColor(Color.BLACK)
        //mEditor.setEditorBackgroundColor(Color.BLUE)
        //mEditor.setBackgroundColor(Color.BLUE)
        //mEditor.setBackgroundResource(R.drawable.bg)
       editor.setPadding(10, 10, 10, 10)
        //    mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg")
        editor.setPlaceholder("Insert text here...")

        editor.setOnTextChangeListener(RichEditor.OnTextChangeListener { text -> })

        ivInsertImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent, OPEN_ALBUM_REQUEST_CODE)
        }


        ivStep.setOnClickListener { editor.undo() }
        ivNext.setOnClickListener { editor.redo() }
        ivLeft.setOnClickListener { editor.setAlignLeft() }
        ivCenter.setOnClickListener { editor.setAlignCenter() }
        ivRight.setOnClickListener { editor.setAlignRight() }
        ivBold.setOnClickListener { editor.setBold() }
        ivItalic.setOnClickListener { editor.setItalic() }
        ivSubscript.setOnClickListener { editor.setSubscript() }
        ivUnderline.setOnClickListener { editor.setUnderline() }

        ivBack.setOnClickListener { finish() }
        ivPush.setOnClickListener { }

        ivH1.setOnClickListener { editor.setHeading(1) }
        ivH2.setOnClickListener { editor.setHeading(2) }
        ivH3.setOnClickListener { editor.setHeading(3) }
        ivH4.setOnClickListener { editor.setHeading(4) }
        ivH5.setOnClickListener { editor.setHeading(5) }
        ivH6.setOnClickListener { editor.setHeading(6) }



        editor.setOnTextChangeListener { text ->
            mContent = text
        }
        ivPush.setOnClickListener {
            mPresenter.push(mContent, edTitle.text.trim().toString(), mCoverUrl)


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == OPEN_ALBUM_REQUEST_CODE) {
                if (data?.data != null) {
                    val uri = data.data
                    val uriPath = CommonUtil.getUriPath(this@CloverEditActivity, uri)
                    mPresenter.uploadImage(uriPath)

                }
            }
        }
    }

}
