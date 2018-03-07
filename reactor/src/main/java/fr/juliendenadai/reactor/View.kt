package fr.juliendenadai.reactor

import io.reactivex.Observable

/**
 * A View displays data.
 */
interface View {

    /**
     * The MVI reactor
     */
    val reactor: Reactor

    /**
     * User action flux
     * @return the observable of action
     */
    fun intents(): Observable<Action>

    /**
     * Render a specific state
     * @param state the state to render
     */
    fun render(state: State)
}
