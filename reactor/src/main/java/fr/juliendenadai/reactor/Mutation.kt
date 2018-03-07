package fr.juliendenadai.reactor

/**
 * A mutation represents state changes.
 */
abstract class Mutation {
    override fun toString(): String {
        return javaClass.simpleName
    }
}
