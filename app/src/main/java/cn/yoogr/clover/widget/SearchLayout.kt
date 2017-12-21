package cn.yoogr.clover.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import cn.yoogr.clover.R
import com.avos.avoscloud.AVFile
import com.avos.avoscloud.AVUser
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by yuequan on 2017/11/24.
 */
class SearchLayout(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs), View.OnClickListener {
    override fun onClick(v: View?) {
        if (listener != null) {
            listener.onClick(v!!)
        }
    }

    interface MyOnClickListener {
        fun onClick(v: View)
    }

    lateinit var listener: MyOnClickListener
    fun setMyOnClickListener(listener: MyOnClickListener) {
        this.listener = listener
    }


    var view: View
    var left: CircleImageView? = null
    var right: ImageView? = null
    var edit: EditText? = null

    init {
        view = View.inflate(context, R.layout.layout_search_view, this)
        left = view.findViewById(R.id.left)
        right = view.findViewById(R.id.right)
        edit = view.findViewById(R.id.search)
        edit?.setOnClickListener(this)
        left?.setOnClickListener(this)
        right?.setOnClickListener(this)
    }








}