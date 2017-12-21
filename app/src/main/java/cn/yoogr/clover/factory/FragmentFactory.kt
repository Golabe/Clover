package cn.yoogr.clover.factory

import android.support.v4.app.Fragment
import cn.yoogr.clover.R
import cn.yoogr.clover.mvp.ui.fragment.CloverFragment
import cn.yoogr.clover.mvp.ui.fragment.MovieFragment
import cn.yoogr.clover.mvp.ui.fragment.PhotoFragment

/**
 * Created by yuequan on 2017/11/24.
 *
 * Fragment Factory  负责创建Fragment
 */
class FragmentFactory {

    companion object {
        val INSTANCE by lazy { FragmentFactory() }

    }

    private val movie by lazy {

        MovieFragment()
    }
    private val clover by lazy {
        CloverFragment()
    }

    private val photo by lazy {
        PhotoFragment()
    }

    fun getFragment(id: Int): Fragment? {
        when (id) {
            R.id.navigation_movie -> return movie
            R.id.navigation_clover -> return clover
            R.id.navigation_photo -> return photo
        }
        return null
    }


}