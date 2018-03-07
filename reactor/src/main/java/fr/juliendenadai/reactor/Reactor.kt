package fr.juliendenadai.reactor

import io.reactivex.ObservableTransformer

/**
 * A Reactor is an UI-independent layer which manages the state of a view
 */
interface Reactor {

    /**
     * Transformer function to process Action and send new State
     */
    val convert: ObservableTransformer<Action, State>
}
