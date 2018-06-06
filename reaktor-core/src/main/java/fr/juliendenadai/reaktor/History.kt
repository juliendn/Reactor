package fr.juliendenadai.reaktor

import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject


/**
 * Tool to store Action and State played
 * Objective is to be able to rewind and replay scenario
 */
class History(val debug: List<Debug>) : IHistory {

    @Suppress("unchecked_cast")
    override val log: Subject<DebugData> by lazy {
        PublishSubject.create<DebugData>().also { subject ->

            val obs = subject
                    .scan(emptyList<DebugData>(), { list, item ->
                        list + item
                    })

            debug.forEach { obs.subscribeWith(it.logger) }
        }
    }
}
