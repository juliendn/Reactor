package fr.juliendenadai.reactor.sample.citylist

import fr.juliendenadai.reactor.*
import fr.juliendenadai.reactor.sample.Routes
import fr.juliendenadai.reactor.sample.SwingExecutor
import fr.juliendenadai.reactor.sample.pojo.MeteoForCity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.swing.DefaultListModel

/**
 * A view using swing framework to render
 */
class SwingView(
        reactor: IReactor,
        private val navigator: INavigator
) : View {

    private val disposables = CompositeDisposable()

    private val list = javax.swing.JList<String>().apply {
        setBounds(10, 10, 380, 500)
    }

    private val loader = javax.swing.JProgressBar().apply {
        setBounds(10, 520, 380, 10)
        isIndeterminate = true
    }

    private val button = javax.swing.JButton("Add city").apply {
        setBounds(10, 540, 380, 30)
    }

    val frame = javax.swing.JFrame().apply {
        add(list)
        add(loader)
        add(button)

        setSize(400, 600)
        layout = null
    }

    init {
        bind(reactor)
    }

    override fun bind(reactor: IReactor) {

        intents()
                .subscribeWith(reactor.actions)
                .onSubscribe(disposables)

        reactor.state
                .observeOn(Schedulers.from(SwingExecutor))
                .subscribe(::render)
                .also { disposables.add(it) }

        navigator.navigation
                .observeOn(Schedulers.from(SwingExecutor))
                .subscribe(::navigatesTo)
                .also { disposables.add(it) }

    }

    private fun intents(): Observable<Action> = Observable.mergeArray(
            lifecycle(),
            click()
    )

    private fun render(state: State) = when (state) {
        is CityListStates.Idle -> renderInitialState()
        is CityListStates.Loading -> renderLoading()
        is CityListStates.Loaded -> renderDatas(state.datas)
        is CityListStates.Error -> renderError(state.error)
        else -> error("Unknown state $state")
    }

    private fun navigatesTo(route: Route): Unit = when (route) {
        is Routes.AddCity -> TODO()
        is Routes.CityList -> TODO()
        else -> error("Unknown route $route")
    }

    private fun click(): Observable<Action> = Observable.create { emitter ->
        button.addActionListener {
            emitter.onNext(CityListActions.AddCity)
        }
    }

    private fun lifecycle(): Observable<Action> = Observable.just<Action>(CityListActions.LoadMeteoCityList)

    private fun renderInitialState() {
        button.isEnabled = true
        loader.isVisible = false
    }

    private fun renderLoading() {
        button.isEnabled = false
        loader.isVisible = true
    }

    private fun renderDatas(datas: List<MeteoForCity>) {
        button.isEnabled = true
        loader.isVisible = false

        list.removeAll()
        val modelList = DefaultListModel<String>()
        datas.forEach {
            modelList.addElement("${it.city}\t\t${it.temperature}")
        }
        list.model = modelList
    }

    private fun renderError(error: Throwable) {
        button.isEnabled = true
        loader.isVisible = false
        TODO("Display error dialog for $error")
    }
}