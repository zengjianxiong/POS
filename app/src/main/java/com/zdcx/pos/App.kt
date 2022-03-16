package com.zdcx.pos

import android.app.Application
import androidx.work.WorkManager
import androidx.work.await
import com.zdcx.pos.di.allModules
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


/**
 * Created by AhmedEltaher
 */

open class App : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            androidFileProperties()
            fragmentFactory()
            workManagerFactory()
            modules(allModules)
        }
    }
}
