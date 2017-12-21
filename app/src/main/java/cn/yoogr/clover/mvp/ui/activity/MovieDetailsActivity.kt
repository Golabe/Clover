package cn.yoogr.clover.mvp.ui.activity


import cn.yoogr.clover.R
import cn.yoogr.clover.base.BaseActivity
import cn.yoogr.clover.mvp.model.data.VideoDetailsBean
import cn.yoogr.clover.mvp.presenter.MovieDetailsPresenter
import cn.yoogr.clover.mvp.view.IMovieDetailsView
import cn.yoogr.clover.util.ImageLoaderUtil
import com.bumptech.glide.Priority
import com.devlin_n.videoplayer.controller.StandardVideoController
import kotlinx.android.synthetic.main.activity_movie_details.*


class MovieDetailsActivity : BaseActivity<MovieDetailsPresenter>(), IMovieDetailsView {

    /**
     * 获取视频详细信息成功回调
     */
    override fun onSuccess(bean: VideoDetailsBean) {
        ImageLoaderUtil.loadImage(this@MovieDetailsActivity, bean.avatarUrl, Priority.NORMAL, cvAvatar)
        tvName.text = bean.author
        tvTitle.text = bean.title
        tvDownloadCount.text = bean.download.toString()
        tvLikeCount.text = bean.like.toString()

    }

    /**
     * 获取视频信息失败回调
     */
    override fun onFailed(code: Int) {

    }

    override fun getLayoutResId(): Int = R.layout.activity_movie_details

    override fun createPresenter(): MovieDetailsPresenter = MovieDetailsPresenter(this)

    private lateinit var mObjectId: String
    override fun init() {
        val bundle = intent.extras
        mObjectId = bundle.getString("objectId")
        val mVideoUrl = bundle.getString("videoUrl")
        mPresenter.getMovieListData(mObjectId)
        val controller = StandardVideoController(this)
        ijkVideoView
                //.autoRotate() //启用重力感应自动进入/推出全屏功能
                .enableCache() //启用边播边缓存功能
               // .useSurfaceView() //启用SurfaceView显示视频，不调用默认使用TextureView
                //  .useAndroidMediaPlayer() //启动AndroidMediaPlayer，不调用此方法默认使用IjkPlayer
                .setUrl(mVideoUrl) //设置视频地址
                //.setTitle("网易公开课-如何掌控你的自由时间") //设置视频标题
                .setVideoController(controller) //设置控制器，如需定制可继承BaseVideoController
                .start() //开始播放，不调用则不自动播放


    }

    override fun onPause() {
        super.onPause()
        ijkVideoView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        ijkVideoView.release()
    }

    override fun onResume() {
        super.onResume()
        ijkVideoView.resume()
    }

    override fun onBackPressed() {
        if (!ijkVideoView.onBackPressed()) {
            super.onBackPressed()
        }
    }
}
