package app

import android.app.Application
import app.di.appModule
import app.di.dataModule
import app.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level


class KoinStart : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin{
            androidLogger( Level.NONE)
            androidContext(this@KoinStart)
            modules(listOf(appModule, domainModule, dataModule))
        }
    }
}