package fr.juliendenadai.reaktor

/**
 * A State represents the current state of a view.
 */
open class State {
    override fun toString(): String {
        return javaClass.simpleName
    }
}
