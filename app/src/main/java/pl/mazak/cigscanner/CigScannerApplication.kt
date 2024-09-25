package pl.mazak.cigscanner

import android.app.Application
import pl.mazak.cigscanner.data.AppContainer
import pl.mazak.cigscanner.data.AppDataContainer

class CigScannerApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}