package fr.juliendenadai.reaktor.sample.debug

import android.util.Log
import com.google.gson.Gson
import fr.juliendenadai.reaktor.Debug
import fr.juliendenadai.reaktor.DebugData
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject

class LogcatDebug(private val tag: String) : Debug {

    override val logger: Observer<List<DebugData>> by lazy {
        PublishSubject.create<List<DebugData>>().apply {
            subscribe {
                Log.d(tag, Gson().toJson(it))
            }
        }
    }
}
