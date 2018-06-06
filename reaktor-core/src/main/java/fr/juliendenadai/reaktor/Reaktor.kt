package fr.juliendenadai.reaktor

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import io.reactivex.subjects.Subject

/**
 * Abstract reactor
 */
abstract class Reaktor(
        history: IHistory
) : IReaktor {

    val debug: Observer<DebugData> by lazy {
        PublishSubject.create<DebugData>().also {
            it.subscribeWith(history.log)
        }
    }

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

    final override val state: Observable<State> by lazy {
        actions
                .doOnNext { pushUiAction(it) }
                .flatMap { mutate(it) }
                .scan(initialState, ::reduce)
                .distinctUntilChanged()
                .doOnNext { pushUiState(it) }
                .replay(1)
                .autoConnect()
    }

    private fun pushUiState(state: State) {
        debug.onNext(DebugData("${javaClass.simpleName}@${Integer.toHexString(hashCode())}", System.currentTimeMillis(), state.toString(), DebugType.STATE))
    }

    private fun pushUiAction(action: Action) {
        debug.onNext(DebugData("${javaClass.simpleName}@${Integer.toHexString(hashCode())}", System.currentTimeMillis(), action.toString(), DebugType.ACTION))
    }
}
