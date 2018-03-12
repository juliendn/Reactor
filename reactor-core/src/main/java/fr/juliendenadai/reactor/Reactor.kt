package fr.juliendenadai.reactor

import io.reactivex.Observable
import io.reactivex.subjects.ReplaySubject
import io.reactivex.subjects.Subject

/**
 * Abstract reactor
 */
abstract class Reactor : IReactor {


    /**
     * State return juste after subscriber
     */
    abstract val initialState: State

    /**
     * Do side effect
     * @param action the action to apply
     * @return the mutations
     */
    abstract fun mutate(action: Action): Observable<Mutation>

    /**
     * Calculate new previousState
     * @param previousState the previous previousState
     * @param mutation the changes made
     * @return the new previousState
     */
    abstract fun reduce(previousState: State, mutation: Mutation): State

    final override val actions: Subject<Action> by lazy { ReplaySubject.create<Action>() }

    final override val state by lazy {
        actions
                .doOnNext(History::pushUiAction)
                .flatMap { mutate(it) }
                .scan(initialState, ::reduce)
                .distinctUntilChanged()
                .doOnNext(History::pushUiState)
                .replay(1)
                .autoConnect()
    }

}