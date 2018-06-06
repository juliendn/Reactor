package fr.juliendenadai.reaktor

import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Base Activity
 */
abstract class ReaktorActivity : AppCompatActivity(), View {

    private val disposables = CompositeDisposable()

    protected abstract val navigator: INavigator

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    override fun bind(reactor: IReaktor) {
        intents()
                .subscribeWith(reactor.actions)
                .onSubscribe(disposables)

        reactor.state
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::render)
                .also { disposables.add(it) }

        navigator.navigation
                .subscribe(::navigates)
                .also { disposables.add(it) }

    }

    abstract fun intents(): Observable<Action>

    abstract fun render(state: State)

    abstract fun navigates(to: Route)
}
