package fr.juliendenadai.reaktor.sample

import android.app.Application
import com.squareup.leakcanary.LeakCanary

class MeteoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }
}
