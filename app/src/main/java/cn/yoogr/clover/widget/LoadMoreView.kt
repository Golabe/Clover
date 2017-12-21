package cn.yoogr.clover.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import cn.yoogr.clover.R

/**
 * Created by yuequan on 2017/12/1.
 */
class LoadMoreView(context: Context?, attrs: AttributeSet?=null) : RelativeLayout(context, attrs) {
    init {

        View.inflate(context, R.layout.item_load_more_view,this)
    }
}