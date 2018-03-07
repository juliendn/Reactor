package fr.juliendenadai.reactor

/**
 * An action represents user actions.
 */
abstract class Action {
    override fun toString(): String {
        return javaClass.simpleName
    }
}
