package com.zdcx.pos.ui.component.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import com.zdcx.pos.ui.base.BaseActivity
import com.zdcx.pos.ui.component.login.LoginActivity
import com.zdcx.pos.SPLASH_DELAY
import com.zdcx.pos.databinding.SplashLayoutBinding


/**
 * Created by AhmedEltaher
 */

class SplashActivity : BaseActivity(){

    private lateinit var binding: SplashLayoutBinding

    override fun initViewBinding() {
        binding = SplashLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToMainScreen()
    }

    override fun observeViewModel() {
    }

    private fun navigateToMainScreen() {
        Handler().postDelayed({
            val nextScreenIntent = Intent(this, LoginActivity::class.java)
            startActivity(nextScreenIntent)
            finish()
        }, SPLASH_DELAY.toLong())
    }
}
