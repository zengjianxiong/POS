package com.zdcx.pos.ui.component.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zdcx.pos.data.DataRepository
import com.zdcx.pos.data.Resource
import com.zdcx.pos.data.dto.login.LoginRequest
import com.zdcx.pos.data.dto.login.LoginResponse
import com.zdcx.pos.data.error.CHECK_YOUR_FIELDS
import com.zdcx.pos.data.error.PASS_WORD_ERROR
import com.zdcx.pos.data.error.USER_NAME_ERROR
import com.zdcx.pos.util.InstantExecutorExtension
import com.zdcx.pos.util.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Created by AhmedEltaher
 */
@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class LoginViewModelTest {
    // Subject under test
    private lateinit var loginViewModel: LoginViewModel

    // Use a fake UseCase to be injected into the viewModel
    private val dataRepository: DataRepository = mockk()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
    }

    @Test
    fun `login Success`() {
        // Let's do an answer for the liveData
        val userName = "ahmed@ahmed.ahmed"
        val password = "ahmed"
        val loginResponse = LoginResponse("123", "Ahmed", "Mahmoud",
                "FrunkfurterAlle", "77", "12000", "Berlin",
                "Germany", "ahmed@ahmed.ahmed")

        //1- Mock calls
        coEvery { dataRepository.doLogin(LoginRequest(userName, password)) } returns flow {
            emit(Resource.Success(loginResponse))
        }

        //2-Call
        loginViewModel = LoginViewModel(dataRepository)
        loginViewModel.doLogin(userName, password)
        //active observer for livedata
        loginViewModel.loginLiveData.observeForever { }

        //3-verify
        val loginSuccess = loginViewModel.loginLiveData.value?.data
        assertEquals(loginSuccess, loginResponse)
    }

    @Test
    fun `login with Wrong Password`() {
        // Let's do an answer for the liveData
        val userName = "ahmed@ahmed.ahmed"
        val password = " "

        //1- Mock calls
        coEvery { dataRepository.doLogin(LoginRequest(userName, password)) } returns flow {
            val result: Resource<LoginResponse> = Resource.DataError(PASS_WORD_ERROR)
            emit(result)
        }

        //2-Call
        loginViewModel = LoginViewModel(dataRepository)
        loginViewModel.doLogin(userName, password)
        //active observer for livedata
        loginViewModel.loginLiveData.observeForever { }

        //3-verify
        val loginFail = loginViewModel.loginLiveData.value?.errorCode
        assertEquals(PASS_WORD_ERROR, loginFail)
    }

    @Test
    fun `login With Wrong User Name`() {
        // Let's do an answer for the liveData
        val userName = " "
        val password = "ahmed"

        //1- Mock calls
        coEvery { dataRepository.doLogin(LoginRequest(userName, password)) } returns flow {
            val result: Resource<LoginResponse> = Resource.DataError(USER_NAME_ERROR)
            emit(result)
        }

        //2-Call
        loginViewModel = LoginViewModel(dataRepository)
        loginViewModel.doLogin(userName, password)
        //active observer for livedata
        loginViewModel.loginLiveData.observeForever { }

        //3-verify
        val loginFail = loginViewModel.loginLiveData.value?.errorCode
        assertEquals(USER_NAME_ERROR, loginFail)
    }

    @Test
    fun `login With Wrong User Name and password`() {
        // Let's do an answer for the liveData
        val userName = " "
        val password = " "

        //1- Mock calls
        coEvery { dataRepository.doLogin(LoginRequest(userName, password)) } returns flow {
            val result: Resource<LoginResponse> = Resource.DataError(CHECK_YOUR_FIELDS)
            emit(result)
        }

        //2-Call
        loginViewModel = LoginViewModel(dataRepository)
        loginViewModel.doLogin(userName, password)
        //active observer for livedata
        loginViewModel.loginLiveData.observeForever { }

        //3-verify
        val loginFail = loginViewModel.loginLiveData.value?.errorCode
        assertEquals(CHECK_YOUR_FIELDS, loginFail)
    }
}
