package fr.juliendenadai.reactor

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Base ViewModel
 */
abstract class MviBaseViewModel : ViewModel(), Reactor {

    /**
     * State return juste after subscriber
     */
    abstract val initialState: State

    /**
     * transformer apply to actions, providing new states
     */
    override val convert = ObservableTransformer<Action, State> { actions ->
        actions
                .doOnNext(History::pushUiEvent)
                .observeOn(Schedulers.io())
                .flatMap(::mutate)
                .scan(initialState, ::reduce)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(History::pushUiModel)
    }

    /**
     * Do side effect
     * @param action the action to apply
     * @return the mutations
     */
    abstract fun mutate(action: Action): Observable<Mutation>

    /**
     * Calculate new state
     * @param state the previous state
     * @param mutation the changes made
     * @return the new state
     */
    abstract fun reduce(state: State, mutation: Mutation): State
}
