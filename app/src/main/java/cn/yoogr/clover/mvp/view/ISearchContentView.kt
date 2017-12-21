package cn.yoogr.clover.mvp.view

/**
 * Created by yuequan on 2017/12/8.
 */
interface ISearchContentView {
    fun searchSuccess()
    fun searchFailed(code:Int)
    fun isEmpty()
}