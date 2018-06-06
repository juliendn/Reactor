package fr.juliendenadai.reaktor

import io.reactivex.Observer

interface Debug {
    val logger: Observer<List<DebugData>>
}
