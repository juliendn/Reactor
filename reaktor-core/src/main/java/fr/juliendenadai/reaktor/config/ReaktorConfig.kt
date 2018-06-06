package fr.juliendenadai.reaktor.config

import fr.juliendenadai.reaktor.Debug

@ReaktorDsl
class ReaktorConfig {

    var debug: List<Debug> = emptyList()

    fun debug(debug: () -> Debug) {
        this.debug += debug()
    }
}
