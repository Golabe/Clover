package cn.yoogr.clover.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.yoogr.clover.R

/**
 * Created by yuequan on 2017/12/13.
 */
class SettingItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var view: View
    lateinit var ic:ImageView
    lateinit var t:TextView
    init {

        view = View.inflate(context, R.layout.layout_setting_view, this)
        ic=view.findViewById(R.id.ic)
        t=view.findViewById(R.id.type)


    }

    fun setImage(resId: Int) {
        ic.setImageResource(resId)
    }

    fun setType(type:String){
        t.text = type
    }
}