package cn.yoogr.clover.mvp.ui.activity

import android.support.v7.app.AlertDialog
import cn.yoogr.clover.R
import cn.yoogr.clover.base.BaseActivity
import cn.yoogr.clover.mvp.model.data.UserInfoBean
import cn.yoogr.clover.mvp.presenter.SpacePresenter
import cn.yoogr.clover.mvp.view.ISpaceView
import cn.yoogr.clover.util.ImageLoaderUtil
import cn.yoogr.clover.util.QiNiuUtil
import com.bumptech.glide.Priority
import kotlinx.android.synthetic.main.activity_space.*
import kotlinx.android.synthetic.main.layout_space_count.*
import org.jetbrains.anko.startActivity

class SpaceActivity : BaseActivity<SpacePresenter>(), ISpaceView {


    /**
     * 获取用户信息成功后  设置用户信息
     */
    override fun getUserInfoSuccess(data: UserInfoBean) {
        ImageLoaderUtil.loadImage(this@SpaceActivity, QiNiuUtil.imageBluring(data.background, 5, 8), Priority.NORMAL, ivHeaderImage)
        ImageLoaderUtil.loadImage(this@SpaceActivity, data.avatarUrl, Priority.NORMAL, cvAvatar)
        tvVideoTotal.text = data.videoTotal.toString()
        tvCloverTotal.text = data.cloverTotal.toString()
        tvPhotoTotal.text = data.photoTotal.toString()
        tvFollowTotal.text = data.follow.toString()
        tvName.text = data.username
        tvNickname.text = data.nickname

        this.mAvatarUrl = data.avatarUrl.toString()
        this.mBirthday = data.birthday.toString()
        this.mUsername = data.username.toString()
        this.mNickname = data.nickname.toString()

    }
    private lateinit var mAvatarUrl: String
    private lateinit var mBirthday: String
    private lateinit var mUsername: String
    private lateinit var mNickname: String
    override fun getLayoutResId(): Int = R.layout.activity_space

    override fun createPresenter(): SpacePresenter = SpacePresenter(this)
    override fun init() {


        ivEdit.setOnClickListener {
            startActivity<EditUserInfoActivity>(
                    "avatarUrl" to mAvatarUrl,
                    "birthday" to mBirthday,
                    "username" to mUsername,
                    "nickname" to mNickname)

        }
        btnLogout.apply {
            setOnClickListener {
                AlertDialog.Builder(this@SpaceActivity)
                        .setTitle("Are you sure you want to quit？")
                        .setMessage("After the withdrawal cannot enter personal space")
                        .setNegativeButton("CANCEL") { p0, p1 -> }.setPositiveButton("EXIT") { p0, p1 ->
                    mPresenter.logout()
                    finish()
                }
                        .show()
            }
        }

        feedback.apply {
            setImage(R.mipmap.ic_feedback)
            setType("Feedback")
        }

        setting.apply {
            setImage(R.mipmap.ic_setting)
            setType("Setting")
        }
        about.apply {
            setImage(R.mipmap.ic_about)
            setType("About")
        }
    }

    override fun onResume() {
        super.onResume()

        //获取用户信息
        mPresenter.getUserInfo()
    }

}
