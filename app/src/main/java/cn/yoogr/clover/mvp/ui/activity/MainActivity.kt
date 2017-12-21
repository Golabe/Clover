package cn.yoogr.clover.mvp.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.support.design.widget.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import android.content.res.ColorStateList
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v4.view.ViewCompat
import android.view.View
import cn.yoogr.clover.R
import cn.yoogr.clover.adapter.MyViewPropertyAnimatorListenerAdapter
import cn.yoogr.clover.base.BaseActivity
import cn.yoogr.clover.factory.FragmentFactory
import cn.yoogr.clover.mvp.model.data.VersionBean
import cn.yoogr.clover.mvp.presenter.MainPresenter
import cn.yoogr.clover.mvp.view.IMainView
import cn.yoogr.clover.util.ActivityJump
import cn.yoogr.clover.widget.*
import com.bumptech.glide.Glide
import com.lzx.nickphoto.utils.CommonUtil
import kotlinx.android.synthetic.main.layout_bottom_sheet_add.*
import kotlinx.android.synthetic.main.layout_search_view.*
import org.jetbrains.anko.startActivity
import java.io.File


class MainActivity : BaseActivity<MainPresenter>(), IMainView {
    override fun getAvatarFailed() {
        left.setImageResource(R.mipmap.ic_avatar_default)
    }

    /**
     * 获取头像
     */
    override fun getAvatarSuccess(url: String?) {
        Glide.with(this@MainActivity).load(url).into(left)
    }

    /**
     * 检测到版本更新回调
     */
    override fun hasVersion(result: VersionBean) {
        //显示版本更新提示Dialog
        VersionUpdateDialog().apply {
            setUpdateInfo(result)
            show(supportFragmentManager, "version_update")
            //点击下载按钮打开下载dialog
            setDownloadClickListener(object : VersionUpdateDialog.OnDownloadClickListener {
                override fun onClick() {
                    dismiss()
                    VersionDownloadDialog().apply {
                        setDownloadInfo(result)
                        show(supportFragmentManager, "download_dialog")
                    }
                }
            })
        }
    }

    /**
     * 如果没有登录  ，跳转到登录界面
     */
    override fun noLogin() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            val compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity, left, "PHOTO")
            startActivity(intent, compat.toBundle())

        }
    }

    /**
     * 如果登录，打开个人中心
     */
    override fun loginIn() {
        val intent = Intent(this@MainActivity, SpaceActivity::class.java)
        val compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity, left, "PHOTO")
        startActivity(intent, compat.toBundle())
    }

    override fun getLayoutResId(): Int = R.layout.activity_main
    override fun createPresenter(): MainPresenter = MainPresenter(this)
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        supportFragmentManager.beginTransaction()
                .replace(R.id.mainContainer, FragmentFactory.INSTANCE.getFragment(item.itemId))
                .commit()
        true
    }
    /**
     * FAB  动画进度监听
     */
    private val fabAnimateListener = object : MyViewPropertyAnimatorListenerAdapter() {
        override fun onAnimationStart(view: View?) {
            createBottomSheetAddDialog()
        }
    }


    override fun init() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 2000)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 2100)
        }
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 2200)
        }

        val file1 = File(Environment.getExternalStorageDirectory().canonicalPath + "/Clover")
        if (!file1.exists()){
            file1.mkdirs()
        }
        val file2 = File(Environment.getExternalStorageDirectory().canonicalPath + "/Clover/image")
        if (!file2.exists()){
            file2.mkdirs()
        }
        mPresenter.getVersionInfo(this)//检查服务器app版本
        initNavigation()
        initSearchLayout()
        initFAB()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 2000) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
        if (requestCode == 2100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
        if (requestCode == 2200) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    /**
     * 初始化搜索布局
     */
    private fun initSearchLayout() {
        searchLayout.apply {
            edit?.isFocusable = false
            edit?.isCursorVisible = false

            setMyOnClickListener(object : SearchLayout.MyOnClickListener {
                override fun onClick(v: View) {
                    when (v.id) {
                        R.id.left -> mPresenter.loginStatus()//检查有没有登录
                        R.id.search -> {
                            ActivityJump.transitionAnimationJump(this@MainActivity, SearchActivity::class.java, searchLayout, "SEARCH")
                        }
                        R.id.right -> {
                        }
                    }
                }
            })
        }
    }


    private fun initFAB() {
        fab.apply {
            setOnClickListener { ViewCompat.animate(fab).rotationBy(90f).setListener(fabAnimateListener).duration = 200 }
        }
    }

    /**
     * 创建拍照，写文章，拍视频dialog
     */
    private fun createBottomSheetAddDialog() {
        AddBottomSheetDialog(this@MainActivity).apply {
            camera.setOnClickListener {
                if (CommonUtil.isLogin()) {
                    startCamera()
                    dismiss()
                } else startActivity<LoginActivity>()
            }
            video.setOnClickListener {
                if (CommonUtil.isLogin()) {
                    startVideo()
                    dismiss()
                } else startActivity<LoginActivity>()

            }
            clover.setOnClickListener {
                if (CommonUtil.isLogin()) {
                    dismiss()
                    startActivity<CloverEditActivity>()
                } else startActivity<LoginActivity>()
            }

            if (isShowing) {
                dismiss()
            } else {
                show()
            }

        }
    }

    private var mPhotoPath: String? = null
    private var mVideoPath: String? = null
    private fun AddBottomSheetDialog.startVideo() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        mVideoPath = Environment.getExternalStorageDirectory().canonicalPath + "/Clover/VIDEO_${CommonUtil.getFilePath()}.mp4"
        val videoPath = FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", File(mVideoPath))
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 8)
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0)
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1024 * 1024)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoPath)
        startActivityForResult(intent, 3000)
    }


    /**
     * 启动相机
     */
    private fun AddBottomSheetDialog.startCamera() {
        //启动相机
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        mPhotoPath = Environment.getExternalStorageDirectory().canonicalPath + "/Clover/PHOTO_${CommonUtil.getFilePath()}.jpg"
        val photoURI = FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", File(mPhotoPath))
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(intent, 1100)
    }


    /**
     * 初始化底部导航按钮
     */
    private fun initNavigation() {
        val states = arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked))
        val colors = intArrayOf(resources.getColor(R.color.home_bottom_normal), resources.getColor(R.color.home_bottom_checked))
        val csl = ColorStateList(states, colors)
        navigation.itemTextColor = csl
        navigation.itemIconTintList = csl
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction()
                .add(R.id.mainContainer, FragmentFactory.INSTANCE.getFragment(R.id.navigation_movie))
                .show(FragmentFactory.INSTANCE.getFragment(R.id.navigation_movie))
                .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {
                1100 -> {
                   startActivity<UploadPhotoActivity>("photoPath" to mPhotoPath)
                }
                3000 -> {
                    startActivity<UploadVideoActivity>("videoUri" to data!!.data, "videoPath" to mVideoPath)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getAvatarData()
    }
}
