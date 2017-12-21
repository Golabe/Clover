package cn.yoogr.clover.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.view.animation.TranslateAnimation

/**
 * Created by yuequan on 2017/12/8.
 *
 * Activity 跳转  动画
 */
object ActivityJump {

    /**
     * 无参数共享元素跳转Activity
     */
    fun <T> transitionAnimationJump(context:Context, clazz: Class<T>, view:View, flag:String){
        val intent = Intent(context, clazz)
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){

            context.startActivity(intent, transitionAnimation(context,view,flag)?.toBundle())
        }else{
            context.startActivity(intent)
        }
    }

    /**
     * 可以携带参数跳转Activity
     */
    fun transitionAnimationJump(intent:Intent, context: Context, view: View, flag: String){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            context.startActivity(intent, transitionAnimation(context,view,flag)?.toBundle())
        }else context.startActivity(intent)
    }

    private fun transitionAnimation(context: Context,view: View,flag: String) : ActivityOptionsCompat? =
            ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, view, flag)
}