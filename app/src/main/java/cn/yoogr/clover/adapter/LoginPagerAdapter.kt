package cn.yoogr.clover.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by yuequan on 2017/11/25.
 */
class LoginPagerAdapter(fm: FragmentManager?, val fragment:List<Fragment>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment =fragment[position]
    override fun getCount(): Int=fragment.size

}