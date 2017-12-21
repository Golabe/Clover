package cn.yoogr.clover.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * Created by yuequan on 2017/12/5.
 */
class PushView :View {

    constructor(context: Context?) : super(context){init()}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){init()}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){init()}


    lateinit var mRadiusPaint:Paint
    lateinit var mTextPaint:Paint
    fun init(){

        mRadiusPaint= Paint()
        mRadiusPaint.isAntiAlias=true
        mRadiusPaint.color=Color.RED
        mRadiusPaint.style=Paint.Style.FILL

        mTextPaint= Paint()
        mTextPaint.isAntiAlias=true
        mTextPaint.color=Color.WHITE


    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val rectF = RectF(0f, 0f, 100f, 100f)
        canvas?.drawArc(rectF,0f,360f,false,mRadiusPaint)

    }
}