package fr.juliendenadai.meteo.addcity

import android.view.View
import com.jakewharton.rxbinding2.widget.editorActionEvents
import fr.juliendenadai.meteo.R
import fr.juliendenadai.meteo.di.ViewModelFactory
import fr.juliendenadai.reactor.Action
import fr.juliendenadai.reactor.MviBaseActivity
import fr.juliendenadai.reactor.State
import io.reactivex.Observable
import kotlinx.android.synthetic.main.addcity_activity.*
import kotlinx.android.synthetic.main.addcity_content.*

class AddCityActivity : MviBaseActivity() {

    override val reactor by ViewModelFactory.provideLazily<AddCityViewModel>(this)

    override fun initUi() {
        setContentView(R.layout.addcity_activity)
        setSupportActionBar(toolbar)
    }

    override fun intents(): Observable<Action> =
            addcity_city.editorActionEvents().map { AddCityActions.SearchCity(it.view().text) }

    override fun render(state: State) {
        when (state) {
            is AddCityStates.Idle -> renderInitialState()
            is AddCityStates.Loading -> renderLoading()
            is AddCityStates.Success -> goBack()
            is AddCityStates.Error -> renderError(state.error)
            else -> error("State unknown $state")
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

    private fun goBack() {
        finish()
    }
}
