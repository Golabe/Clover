package cn.yoogr.clover.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by yuequan on 2017/11/24.
 */
abstract class BaseFragment<P : BasePresenter> : Fragment() {


    lateinit var mPresenter: P
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater?.inflate(getLayoutResId(), null)
    abstract fun getLayoutResId(): Int

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter=createPresenter()
        init()
    }

    abstract fun createPresenter(): P

    open fun init() {

    }


}