package cn.yoogr.clover.mvp.ui.fragment

import cn.yoogr.clover.R
import cn.yoogr.clover.base.BaseFragment
import cn.yoogr.clover.mvp.presenter.RegisterPresenter
import cn.yoogr.clover.mvp.ui.activity.UpdateUserActivity
import cn.yoogr.clover.mvp.view.IRegisterView
import cn.yoogr.clover.widget.DownTimerTextView
import kotlinx.android.synthetic.main.fragment_register.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * Created by yuequan on 2017/11/24.
 */
class RegisterFragment : BaseFragment<RegisterPresenter>(), IRegisterView {
    override fun sendCodeSuccess() {
        toast("验证码发送成功")
    }

    override fun sendCodeFailed() {
    }

    override fun onPhoneInputError() {
        inputPhone.error = getString(R.string.input_phone_error)
    }

    override fun onCodeInputError() {
        toast(getString(R.string.input_code_error))
    }

    override fun onRegisterSuccess() {
        startActivity<UpdateUserActivity>()
        activity.finish()
    }

    override fun onRegisterFailed(status: Int) {
    }

    override fun getLayoutResId(): Int = R.layout.fragment_register

    override fun createPresenter(): RegisterPresenter = RegisterPresenter(this)

    override fun init() {
        /**
         * 注册按钮
         */
        next.apply {

            next.apply {
                setText(R.string.next)
                setOnClickListener {
                    next()
                }
            }
        }
        /**
         * 获取验证码按钮
         */
        getCode.apply {
            setTimerClickListener(object : DownTimerTextView.TimerListener {
                override fun onStartTimer() {
                    sendCode()
                }
            })
        }
    }

    /**
     * 发送验证码
     */
    private fun sendCode() {
        val p = phone.text.toString()
        mPresenter.sendCode(p)
    }

    /**
     *  注册
     */
    private fun next() {
        val p = phone.text.toString()
        val cod = code.text.toString()
        mPresenter.onRegister(p, cod)
    }

}
