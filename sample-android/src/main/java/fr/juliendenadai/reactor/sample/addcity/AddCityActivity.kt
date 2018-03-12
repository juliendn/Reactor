package fr.juliendenadai.reactor.sample.addcity

import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding2.widget.editorActionEvents
import fr.juliendenadai.reactor.*
import fr.juliendenadai.reactor.sample.R
import fr.juliendenadai.reactor.sample.Routes
import fr.juliendenadai.reactor.sample.di.inject
import fr.juliendenadai.reactor.sample.di.lazyViewModel
import io.reactivex.Observable
import kotlinx.android.synthetic.main.addcity_activity.*
import kotlinx.android.synthetic.main.addcity_content.*

class AddCityActivity : ReactorActivity() {

    val reactor by lazyViewModel<AddCityViewModel>()

    override val navigator by inject<INavigator>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.addcity_activity)
        setSupportActionBar(toolbar)

        bind(reactor)
    }

    override fun intents(): Observable<Action> =
            addcity_city.editorActionEvents().map { AddCityActions.SearchCity(it.view().text) }

    override fun render(state: State) {
        when (state) {
            is AddCityStates.Idle -> renderInitialState()
            is AddCityStates.Loading -> renderLoading()
            is AddCityStates.Error -> renderError(state.error)
            else -> error("State unknown $state")
        }
    }

    override fun navigates(to: Route) = when (to) {
        Routes.CityList -> finish()
        else -> {
            // do nothing
        }
    }

    private fun renderError(error: Throwable) {
        addcity_loader.visibility = View.GONE
        addcity_city.isEnabled = true
        addcity_city_container.isErrorEnabled = true
        addcity_city_container.error = error.message
    }

    private fun renderLoading() {
        addcity_loader.visibility = View.VISIBLE
        addcity_city.isEnabled = false
        addcity_city_container.isErrorEnabled = false
    }

    private fun renderInitialState() {
        addcity_loader.visibility = View.GONE
        addcity_city.isEnabled = true
        addcity_city_container.isErrorEnabled = false
    }
}
