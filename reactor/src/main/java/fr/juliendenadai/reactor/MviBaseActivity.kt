package fr.juliendenadai.reactor

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

/**
 * Base Activity
 */
abstract class MviBaseActivity : AppCompatActivity(), View {

    /**
     * Rx disposable
     */
    private val disposables = CompositeDisposable()

    /**
     * initialize Ui component
     */
    abstract fun initUi()

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        bindIntents()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    /**
     * Bind intent to reactor and reactor to renderer
     */
    private fun bindIntents() {
        intents().compose(reactor.convert).subscribe(::render)
    }
}
