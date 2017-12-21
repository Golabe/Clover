package cn.yoogr.clover.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import cn.yoogr.clover.R
import cn.yoogr.clover.base.BaseActivity
import cn.yoogr.clover.mvp.model.data.PhotoDetailsBean
import cn.yoogr.clover.mvp.presenter.PhotoDetailsPresenter
import cn.yoogr.clover.mvp.view.IPhotoDetailsView
import cn.yoogr.clover.util.ImageLoaderUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.lzx.nickphoto.utils.CommonUtil
import kotlinx.android.synthetic.main.activity_photo_details.*
import kotlinx.android.synthetic.main.layout_photo_details.*
import kotlinx.android.synthetic.main.layout_photo_details_navigation.*
import org.jetbrains.anko.startActivity

class PhotoDetailsActivity : BaseActivity<PhotoDetailsPresenter>(), IPhotoDetailsView {

    /**
     * 当前图没有点赞则点赞
     */
    override fun like() {
        mPresenter.like(mObjectId)
        CommonUtil.showSnackBar(btnLike, "LIKE")
    }

    /**
     * 如果当前图片已经点过赞  取消点赞
     */
    override fun cancelLike() {
        mPresenter.cancelLike(mObjectId)
        CommonUtil.showSnackBar(btnLike, "CANCEL LIKE")
    }

    /**
     * 壁纸设置成功回调
     */
    override fun setWallpaperSuccess() {
        CommonUtil.showSnackBar(btnWallpaper, "SETTING SUCCESS")
    }

    /**
     * 壁纸设置失败回调
     */
    override fun setWallpaperFailed() {
        CommonUtil.showSnackBar(btnWallpaper, "SETTING FAILED")

    }

    /**
     * 壁纸已经设置回调
     */
    override fun wallpaperHasBeenSet() {
        CommonUtil.showSnackBar(btnWallpaper, "HAS BEEN SET")
    }

    /**
     * 点赞成功回调
     */
    override fun likeSuccess() {

    }

    /**
     * 点赞失败回调
     */
    override fun likeFailed(code: Int) {
    }

    /**
     * 图片保存失败回调
     */
    override fun savePictureFailed() {
        CommonUtil.showSnackBar(btnDownload, "DOWNLOAD FAILED")
    }

    /**
     * 图片保存成功回调
     */
    override fun savePictureSuccess() {
        CommonUtil.showSnackBar(btnDownload, "DOWNLOAD SUCCESS")

    }

    /**
     * 图片已经存在回调
     */
    override fun pictureSaved() {
        CommonUtil.showSnackBar(btnDownload, "HAS BEEN DOWNLOAD")
    }

    /**
     * 取消点赞成功回调
     */
    override fun cancelLikeSuccess() {

    }

    /**
     * 取消点赞失败回调
     */
    override fun cancelLikeFailed(code: Int) {
    }

    /**
     * 计数成功回调
     */
    override fun onCountSuccess(type: Int) {

    }

    /**
     * 计数失败回调
     */
    override fun onCountFailed(code: Int) {

    }

    private lateinit var mImageUrl: String
    private lateinit var mObjectId: String
    private lateinit var mAuthorId: String
    private lateinit var mImageName: String
    /**
     * 获取图片详情成功回调
     */
    override fun onSuccess(data: PhotoDetailsBean) {
        ImageLoaderUtil.loadImage(this, data.imageUrl!!, Priority.IMMEDIATE, image)
        Glide.with(this).load(data.avatarUrl).into(cvAvatar)
        tvAuthor.text = data.avatar
        mImageUrl = data.imageUrl
        createDate.text = data.createDate.toString()
        createLocation.text = data.location
        likes.text = data.likes.toString() + " LIKE"
        downloadCount.text = data.download.toString() + " DOWNLOAD"
        color.text = data.color
        size.text = data.size.toString()
        mAuthorId = data.authorId!!
        mImageName = data.imageName!!
    }

    /**
     * 获取图片失败回调
     */
    override fun onFailed(code: Int) {
    }

    override fun getLayoutResId(): Int = R.layout.activity_photo_details
    override fun createPresenter(): PhotoDetailsPresenter = PhotoDetailsPresenter(this)
    override fun init() {
        mObjectId = intent.getStringExtra("photoId") as String

        btnBack.apply {
            setOnClickListener { finish() }
        }
        btnWallpaper.apply {
            setOnClickListener {
                mPresenter.setWallpaper(this@PhotoDetailsActivity, mImageUrl, mObjectId)
            }
        }
        cvAvatar.apply {
            setOnClickListener {
                val intent = Intent(this@PhotoDetailsActivity, AuthorActivity::class.java)
                val bundle = Bundle()
                bundle.putString("authorId", mAuthorId)
                intent.putExtras(bundle)
                val compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this@PhotoDetailsActivity, cvAvatar, "PHOTO")
                startActivity(intent, compat.toBundle())
            }
        }
        btnLike.apply {
            setOnClickListener {
                if (CommonUtil.isLogin()) {
                    mPresenter.likeStatus(mObjectId)
                } else startActivity<LoginActivity>()
            }
        }
        btnDownload.apply {
            setOnClickListener {
                mPresenter.download(this@PhotoDetailsActivity, mImageName, mImageUrl, mObjectId)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        mPresenter.getPhotoDetailsData(mObjectId)
    }
}
