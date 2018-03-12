package fr.juliendenadai.reactor

import io.reactivex.Observable
import io.reactivex.Observer

/**
 * A Reactor is an UI-independent layer which manages the state of a view
 */
interface IReactor {

    /**
     * Transformer function to process Action and send new State
     */
//    val convert: ObservableTransformer<Action, State>

    val actions: Observer<Action>

    val state: Observable<State>
}
