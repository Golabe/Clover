package cn.yoogr.clover.widget

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.widget.TextView
import buzz.pushfit.im.extention.isValidPhone


/**
 * Created by yuequan on 2017/11/27.
 *
 * Custom   CountDownTimerTextView
 */
class DownTimerTextView : TextView {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    companion object {
        val TOTAL_TIMER = 50000L
        val DURATION = 1000L

    }

    private var listener: TimerListener? = null
    fun setTimerClickListener(listener:TimerListener){
        this.listener=listener
    }

    interface TimerListener {
        fun onStartTimer()

    }

    init {
        text = "获取验证码"
        setOnClickListener {
            if (listener != null) {
                listener!!.onStartTimer()
                timer.start()
                isEnabled = false
            }
        }
    }

    /**
     *倒计时
     */
    private val timer = object : CountDownTimer(TOTAL_TIMER, DURATION) {
        override fun onFinish() {
            text = "获取验证码"
            isEnabled = true
        }
        override fun onTick(p0: Long) {
            text = (p0 / DURATION).toString() + "秒后重试"
        }

    }
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        timer.cancel()
    }

}