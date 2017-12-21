package cn.yoogr.clover.mvp.ui.activity
import cn.yoogr.clover.R
import cn.yoogr.clover.base.BaseActivity
import cn.yoogr.clover.mvp.model.data.CloverDetailsBean
import cn.yoogr.clover.mvp.presenter.CloverDetailsPresenter
import cn.yoogr.clover.mvp.view.ICloverDetailsView
import cn.yoogr.clover.util.ImageLoaderUtil
import com.bumptech.glide.Priority
import kotlinx.android.synthetic.main.activity_clover_details.*
import org.jetbrains.anko.startActivity


class CloverDetailsActivity : BaseActivity<CloverDetailsPresenter>(), ICloverDetailsView {
    override fun onSuccess(data: CloverDetailsBean) {
        this.mAuthorId= data.authorId!!
        wvVebView.loadDataWithBaseURL(null,
                "<head><style>img{max-width:100% !important;} table{max-width:100% !important;}</style></head>" + data.content, "text/html", "utf-8", null)
        ImageLoaderUtil.loadImage(this@CloverDetailsActivity, data.avatarUrl, Priority.NORMAL, cvAvatar)
        tvAuthor.text = data.author
        tvTitle.text = data.title
        tvCreateAt.text = data.createAt.toString()

    }

    override fun onFailed(code: Int) {
    }

    override fun getLayoutResId(): Int = R.layout.activity_clover_details

    override fun createPresenter(): CloverDetailsPresenter = CloverDetailsPresenter(this)

    private lateinit var mCloverId: String
    private lateinit var mAuthorId: String

    override fun init() {
        val bundle = intent.extras
        mCloverId = bundle.getString("cloverId")

        ivBack.setOnClickListener { finish() }

        cvAvatar.setOnClickListener { startActivity<AuthorActivity>("authorId" to mAuthorId) }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getCloverDetailsData(mCloverId)
    }
}
