package fr.juliendenadai.reaktor.sample

import fr.juliendenadai.reaktor.Debug
import fr.juliendenadai.reaktor.DebugData
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject

class SystemOutDebugger : Debug {

    override val logger: Observer<List<DebugData>> by lazy {
        PublishSubject.create<List<DebugData>>().apply {
            subscribe { System.out.println(it) }
        }
    }

}
