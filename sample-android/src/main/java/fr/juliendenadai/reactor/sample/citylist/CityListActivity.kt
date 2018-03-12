package fr.juliendenadai.reactor.sample.citylist

import android.content.Intent
import android.os.Bundle
import android.support.transition.TransitionManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import fr.juliendenadai.reactor.*
import fr.juliendenadai.reactor.sample.R
import fr.juliendenadai.reactor.sample.Routes
import fr.juliendenadai.reactor.sample.addcity.AddCityActivity
import fr.juliendenadai.reactor.sample.di.inject
import fr.juliendenadai.reactor.sample.di.lazyViewModel
import fr.juliendenadai.reactor.sample.pojo.MeteoForCity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.citylist_activity.*
import kotlinx.android.synthetic.main.citylist_content.*

class CityListActivity : ReactorActivity() {

    private val reactor by lazyViewModel<CityListViewModel>()

    override val navigator by inject<INavigator>()

    private val adapter = CityListAdapter()

    private val lifeCycle: Subject<Action> = PublishSubject.create<Action>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.citylist_activity)
        setSupportActionBar(toolbar)

        citylist_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        citylist_list.adapter = adapter

        bind(reactor)

        if (null == savedInstanceState) {
            lifeCycle.onNext(CityListActions.LoadMeteoCityList)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        lifeCycle.onNext(CityListActions.LoadMeteoCityList)
//    }

    override fun intents(): Observable<Action> = Observable.mergeArray(
            lifeCycle,
            click()
    )

    override fun render(state: State) = when (state) {
        is CityListStates.Idle -> renderInitialState()
        is CityListStates.Loading -> renderLoading()
        is CityListStates.Loaded -> renderDatas(state.datas)
        is CityListStates.Error -> renderError(state.error)
        else -> error("Unknown state $state")
    }

    override fun navigates(to: Route) = when (to) {
        Routes.AddCity -> {
            val intent = Intent(this, AddCityActivity::class.java)
            startActivityForResult(intent, ADD_CITY)
        }
        else -> {
            // do nothing
        }
    }

    private fun click() = RxView.clicks(fab)
            .map { CityListActions.AddCity }

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
        adapter.updateDatas(emptyList())
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
        TODO("Display error dialog for $error")
    }

    companion object {
        private const val ADD_CITY = 1
    }
}
