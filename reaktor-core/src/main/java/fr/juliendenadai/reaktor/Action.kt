package fr.juliendenadai.reaktor

/**
 * An action represents user actions.
 */
open class Action {
    override fun toString(): String {
        return javaClass.simpleName
    }
}
