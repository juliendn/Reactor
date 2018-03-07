package fr.juliendenadai.meteo.citylist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.transition.TransitionManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import fr.juliendenadai.meteo.R
import fr.juliendenadai.meteo.addcity.AddCityActivity
import fr.juliendenadai.meteo.di.ViewModelFactory
import fr.juliendenadai.meteo.pojo.MeteoForCity
import fr.juliendenadai.reactor.Action
import fr.juliendenadai.reactor.MviBaseActivity
import fr.juliendenadai.reactor.State
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.citylist_activity.*
import kotlinx.android.synthetic.main.citylist_content.*

//import kotlinx.android.synthetic.main.citylist_content.*

class CityListActivity : MviBaseActivity() {

    override val reactor by ViewModelFactory.provideLazily<CityListViewModel>(this)

    private val adapter = CityListAdapter()

    private val lifeCycle: Subject<Unit> = PublishSubject.create<Unit>()

    override fun initUi() {
        setContentView(R.layout.citylist_activity)
        setSupportActionBar(toolbar)

        citylist_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        citylist_list.adapter = adapter

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (null == savedInstanceState) {
            lifeCycle.onNext(Unit)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == requestCode && Activity.RESULT_OK == resultCode) {
            lifeCycle.onNext(Unit)
        }
    }

    override fun intents(): Observable<Action> = Observable.mergeArray(
            lifeCycle
                    .map { CityListActions.LoadMeteoCityList },
            RxView.clicks(fab)
                    .map { CityListActions.AddCity }
    )

    override fun render(state: State) = when (state) {
        is CityListStates.Idle -> renderInitialState()
        is CityListStates.Loading -> renderLoading()
        is CityListStates.Loaded -> renderDatas(state.datas)
        is CityListStates.Error -> renderError(state.error)
        is CityListStates.NavigateToAddCity -> goToAddCity()
        else -> error("Unknown state $state")
    }

    private fun renderInitialState() {
        TransitionManager.beginDelayedTransition(root)
        citylist_progress.visibility = View.GONE
        fab.apply {
            isEnabled = false
            visibility = View.GONE
        }
    }

    private fun renderLoading() {
        TransitionManager.beginDelayedTransition(root)
        citylist_progress.visibility = View.VISIBLE
        fab.apply {
            isEnabled = false
            visibility = View.GONE
        }
    }

    private fun renderDatas(datas: List<MeteoForCity>) {
        TransitionManager.beginDelayedTransition(root)
        citylist_progress.visibility = View.GONE
        adapter.updateDatas(datas)
        fab.apply {
            isEnabled = true
            visibility = View.VISIBLE
        }
    }

    private fun renderError(error: Throwable) {
        TransitionManager.beginDelayedTransition(root)
        citylist_progress.visibility = View.GONE
        displayErrorDialog(error)
        fab.apply {
            isEnabled = false
            visibility = View.GONE
        }
    }

    private fun displayErrorDialog(error: Throwable) {
        TODO("faire une popup pour error $error")
    }

    private fun goToAddCity() {
        val intent = Intent(this, AddCityActivity::class.java)
        startActivityForResult(intent, ADD_CITY)
    }

    companion object {
        private const val ADD_CITY = 1
    }
}
