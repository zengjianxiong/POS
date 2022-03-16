package com.zdcx.pos.di

import android.content.Context
import com.zdcx.pos.BASE_URL
import com.zdcx.pos.data.DataRepository
import com.zdcx.pos.data.DataRepositorySource
import com.zdcx.pos.data.error.mapper.ErrorMapper
import com.zdcx.pos.data.local.AppDatabase
import com.zdcx.pos.data.local.LocalData
import com.zdcx.pos.data.remote.RemoteData
import com.zdcx.pos.data.remote.ServiceGenerator
import com.zdcx.pos.errors.ErrorManager
import com.zdcx.pos.ui.component.details.DetailsActivity
import com.zdcx.pos.ui.component.details.DetailsViewModel
import com.zdcx.pos.ui.component.login.LoginActivity
import com.zdcx.pos.ui.component.login.LoginViewModel
import com.zdcx.pos.ui.component.recipes.RecipesListActivity
import com.zdcx.pos.ui.component.recipes.RecipesListViewModel
import com.zdcx.pos.utils.Network
import com.zdcx.pos.utils.NetworkConnectivity
import com.zeng.stationa.data.repository.AppDispatchers

import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module


val appModule = module {
    single {
        Network(androidContext()) as NetworkConnectivity
    }
    factory { AppDispatchers(Dispatchers.Main, Dispatchers.IO) }
}

val dataModule = module {
    single(StringQualifier("DATABASE")) { AppDatabase.buildDatabase(androidContext()) }
    factory {
        (get(StringQualifier("DATABASE")) as AppDatabase).userDao()
    }
    factory {
        LocalData(androidContext(), get(StringQualifier("DATABASE")))
    }

    factory { RemoteData(get(), get()) }
}

val featureModule = module {

    factory { DataRepository(get(), get(), get()) as DataRepositorySource }

    viewModel { LoginViewModel(get()) }
    viewModel { RecipesListViewModel(get()) }
    viewModel { DetailsViewModel(get()) }

}

val errorModule = module {
    single { ErrorManager(get()) }
    single { ErrorMapper(androidContext()) }
}

fun createRemoteModule(baseUrl: String) = module {
    single { ServiceGenerator(baseUrl) }
}


val allModules = appModule + dataModule + featureModule + errorModule + createRemoteModule(BASE_URL)
