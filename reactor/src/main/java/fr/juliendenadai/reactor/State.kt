package fr.juliendenadai.reactor

/**
 * A State represents the current state of a view.
 */
abstract class State {
    override fun toString(): String {
        return javaClass.simpleName
    }
}
