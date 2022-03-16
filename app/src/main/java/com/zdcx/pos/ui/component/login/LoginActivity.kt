package com.zdcx.pos.ui.component.login

import android.content.Intent
import android.os.Bundle

import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.zdcx.pos.data.Resource
import com.zdcx.pos.data.dto.login.LoginResponse
import com.zdcx.pos.databinding.LoginActivityBinding

import com.zdcx.pos.ui.base.BaseActivity
import com.zdcx.pos.utils.*
import com.zdcx.pos.ui.component.recipes.RecipesListActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by AhmedEltaher
 */

class LoginActivity : BaseActivity() {

    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var binding: LoginActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.login.setOnClickListener { doLogin() }
    }

    override fun initViewBinding() {
        binding = LoginActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun observeViewModel() {
        observe(loginViewModel.loginLiveData, ::handleLoginResult)
        observeSnackBarMessages(loginViewModel.showSnackBar)
        observeToast(loginViewModel.showToast)
    }

    private fun doLogin() {
        loginViewModel.doLogin(
            binding.username.text.trim().toString(),
            binding.password.text.toString()
        )
    }

    private fun handleLoginResult(status: Resource<LoginResponse>) {
        when (status) {
            is Resource.Loading -> binding.loaderView.toVisible()
            is Resource.Success -> status.data?.let {
                binding.loaderView.toGone()
                navigateToMainScreen()
            }
            is Resource.DataError -> {
                binding.loaderView.toGone()
                status.errorCode?.let { loginViewModel.showToastMessage(it) }
            }
        }
    }

    private fun navigateToMainScreen() {
        val nextScreenIntent = Intent(this, RecipesListActivity::class.java)
        startActivity(nextScreenIntent)
        finish()
    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }
}
