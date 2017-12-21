package cn.yoogr.clover.mvp.ui.fragment

import cn.yoogr.clover.R
import cn.yoogr.clover.base.BaseFragment
import cn.yoogr.clover.mvp.presenter.LoginPresenter
import cn.yoogr.clover.mvp.view.ILoginView
import cn.yoogr.clover.widget.DownTimerTextView
import cn.yoogr.clover.widget.LoginButtonLayout
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by yuequan on 2017/11/24.
 */
class LoginFragment : BaseFragment<LoginPresenter>(), ILoginView {
    override fun onPhoneInputError() {
        inputPhone.error = getString(R.string.input_phone_error)
    }


    //验证码输入失败回调
    override fun onCodeInputError() {
    }

    //登陆成功回调
    override fun onLoginSuccess() {
        activity.finish()
    }

    //登录失败回调
    override fun onLoginFailed(msg: String) {
       toast(msg)
        login.loadError()
    }

    //烟瘴吗发送成功回调
    override fun sendCodeSuccess() {
        inputPhone.error = ""
    }

    //验证码发送失败回调
    override fun sendCodeFailed() {
    }

    //创建Presenter
    override fun createPresenter(): LoginPresenter = LoginPresenter(this)

    override fun getLayoutResId(): Int = R.layout.fragment_login
    override fun init() {

        login.apply {
            btn.text = context.getString(R.string.login)

            setMyOnClickListener(object :LoginButtonLayout.MyOnClickListener{
                override fun onClick() {
                    login()
                }
            })
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
    fun sendCode() {
        val p = phone.text.toString()
        mPresenter.sendCode(p)
    }

    /**
     * 登录
     */
    private fun login() {
        val p = phone.text.toString()
        val cd = code.text.toString()
        mPresenter.onLogin(p, cd)
    }

}
