package cn.yoogr.clover.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import cn.yoogr.clover.R

/**
 * Created by yuequan on 2017/11/14.
 */

class WaveView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private var w: Int = 0
    private var h: Int = 0
    private var paint: Paint? = null
    private val radius = floatArrayOf(0f, 0f, 0f, 0f)
    private var length: Int = 0
    private val alpha = intArrayOf(255, 255, 255, 255)
    private var textPaint: Paint? = null
    private var progress: Int = 0
    companion object {
        private val DELAYS = longArrayOf(0, 500, 1000, 1500)
        private val DURATION = 2000
    }

    init {
        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.color = resources.getColor(R.color.home_bottom_checked)


        //设置文本Paint
        textPaint = Paint()
        textPaint!!.textSize = 50f
        textPaint!!.color = Color.WHITE
        textPaint!!.strokeWidth = 2f
        textPaint!!.textAlign = Paint.Align.CENTER
        textPaint!!.style = Paint.Style.FILL
        textPaint!!.isAntiAlias = true

//        paint!!.style=Paint.Style.STROKE
//        paint!!.strokeWidth=5f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.w = w
        this.h = h
        length = Math.min(w, h)
        prepareAnimators()
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until radius.size) {
            paint!!.alpha = alpha[i]
            canvas.drawCircle((w / 2).toFloat(), (h / 2).toFloat(), radius[i], paint!!)
        }
        val fontMetrics = textPaint?.fontMetrics
        val top = fontMetrics?.top//为基线到字体上边框的距离,即上图中的top
        val bottom = fontMetrics?.bottom//为基线到字体下边框的距离,即上图中的bottom
        val baseLineY = ((w / 2) - top!! / 2 - bottom!! / 2) //基线中间点的y轴计算公式

        canvas.drawText("$progress %", (w / 2).toFloat(), baseLineY, textPaint)

    }

    private fun prepareAnimators() {

        for (i in 0 until radius.size) {

            var va1 = ValueAnimator.ofFloat(5f, length / 2 - 5f)
            va1.duration = DURATION.toLong()
            va1.repeatCount = ValueAnimator.INFINITE
            va1.startDelay = DELAYS[i]
            va1.start()
            va1.addUpdateListener { valueAnimator ->
                radius[i] = valueAnimator.animatedValue as Float
                invalidate()
            }
            va1 = ValueAnimator.ofInt(255, 0)
            va1.duration = DURATION.toLong()
            va1.repeatCount = ValueAnimator.INFINITE
            va1.startDelay = DELAYS[i]
            va1.start()
            va1.addUpdateListener { valueAnimator -> alpha[i] = valueAnimator.animatedValue as Int }


        }
    }

    /**
     * 设置进度
     */
    fun setProgress(p: Int?) {
        this.progress = p!!
    }

}
