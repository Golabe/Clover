package cn.yoogr.clover.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import cn.yoogr.clover.R

/**
 * Created by yuequan on 2017/11/25.
 */
class LoginButtonLayout(context: Context?, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    var view: View

    var progress: ProgressBar
    var btn: Button

    init {
        view = View.inflate(context, R.layout.layout_login_button, this)
        progress = view.findViewById(R.id.progress)
        btn = findViewById(R.id.start)

        btn.apply {
            setOnClickListener {
                visibility=View.GONE
                progress.visibility= View.VISIBLE
                listener?.onClick()
            }
        }


    }
    fun setMyOnClickListener(listener:MyOnClickListener){
        this.listener=listener
    }
    private var listener:MyOnClickListener ?= null

    interface  MyOnClickListener{
        fun onClick()
    }

    fun loadError(){
        btn.visibility= View.VISIBLE
        progress.visibility= View.GONE
    }
}