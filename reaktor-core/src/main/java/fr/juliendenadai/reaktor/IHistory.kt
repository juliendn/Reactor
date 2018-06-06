package fr.juliendenadai.reaktor

import io.reactivex.Observer

interface IHistory {
    val log: Observer<DebugData>
}
