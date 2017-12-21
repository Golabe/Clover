package cn.yoogr.clover.mvp.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import cn.yoogr.clover.R
import cn.yoogr.clover.adapter.LoginPagerAdapter
import cn.yoogr.clover.adapter.MyOnPageChangeListenerAdapter
import cn.yoogr.clover.mvp.ui.fragment.LoginFragment
import cn.yoogr.clover.mvp.ui.fragment.RegisterFragment
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var mFragments = ArrayList<Fragment>()
    val login by lazy { LoginFragment() }
    val register by lazy { RegisterFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initFragments()
        viewPager.addOnPageChangeListener(object : MyOnPageChangeListenerAdapter() {
            override fun onPageSelected(position: Int) {

                //如果position==0设置为登录字样否则为注册
                if (position == 0) {
                    loginOrRegister.text = getString(R.string.login)
                } else loginOrRegister.text = getString(R.string.register)
            }
        })

        btnBack.setOnClickListener { finishAfterTransition() }
    }

    private fun initFragments() {
        mFragments.add(login)
        mFragments.add(register)
        viewPager.adapter = LoginPagerAdapter(supportFragmentManager, mFragments)

    }
}
