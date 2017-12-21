package cn.yoogr.clover.widget

import android.content.Context
import android.support.design.widget.BottomSheetDialog
import android.support.v4.view.ViewCompat
import android.view.View
import android.widget.ImageView
import cn.yoogr.clover.R
import cn.yoogr.clover.adapter.MyViewPropertyAnimatorListenerAdapter

/**
 * Created by yuequan on 2017/11/24.
 */
class AddBottomSheetDialog(context: Context) : BottomSheetDialog(context) {
    private var view: View = View.inflate(context, R.layout.layout_bottom_sheet_add, null)
    private var clear: ImageView
    private var camera:ImageView
    private var video:ImageView
    private var clover:ImageView


    //执行动画监听
    private val listener = object : MyViewPropertyAnimatorListenerAdapter() {
            override fun onAnimationEnd(view: View?) {
                //关闭Dialog
                dismiss()
            }
    }
    companion object {
        val CLOSE_DURATION =200L//关闭按钮动画时间
        val DURATION=1000L//执行时间
    }

    init {
        setContentView(view)
        clear = view.findViewById(R.id.clear)
        clover = view.findViewById(R.id.clover)
        video = view.findViewById(R.id.video)
        camera = view.findViewById(R.id.camera)
        setAlpha(clover)
        setAlpha(video)
        setAlpha(camera)
        setAlpha(clear)
        clear.apply {
            setOnClickListener {
                ViewCompat.animate(clear).rotation(90f).alpha(0.0f).setListener(listener).duration = CLOSE_DURATION
            }
        }
    }
    /**
     * 设置渐变动画
     */
    private fun setAlpha(v:View){
        ViewCompat.animate(v).alphaBy(1.0f).duration= DURATION
    }

}