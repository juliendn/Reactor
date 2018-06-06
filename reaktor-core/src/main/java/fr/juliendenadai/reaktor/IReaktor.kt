package fr.juliendenadai.reaktor

import io.reactivex.Observable
import io.reactivex.Observer

/**
 * A Reactor is an UI-independent layer which manages the state of a view
 */
interface IReaktor {

    val actions: Observer<Action>

    val state: Observable<State>
}
