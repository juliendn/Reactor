package fr.juliendenadai.reaktor

/**
 * A mutation represents state changes.
 */
open class Mutation {
    override fun toString(): String {
        return javaClass.simpleName
    }
}
