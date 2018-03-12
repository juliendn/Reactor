package fr.juliendenadai.reactor.sample.citylist

import fr.juliendenadai.reactor.*
import fr.juliendenadai.reactor.sample.Routes
import fr.juliendenadai.reactor.sample.services.IMeteoService
import io.reactivex.Observable

/**
 * A simple reactor
 */
class CityListReactor(
        private val service: IMeteoService,
        private val navigator: INavigator) : Reactor() {

    override val initialState: State = CityListStates.Idle

    override fun mutate(action: Action): Observable<Mutation> = when (action) {
        is CityListActions.LoadMeteoCityList -> loadDatas()
        is CityListActions.AddCity -> addCity()
        else -> error("Unknown action $action")
    }

    override fun reduce(previousState: State, mutation: Mutation): State = when (mutation) {
        is CityListMutations.LoadMeteoCityListResult.InFlight -> CityListStates.Loading
        is CityListMutations.LoadMeteoCityListResult.Success -> CityListStates.Loaded(mutation.meteoCity)
        is CityListMutations.LoadMeteoCityListResult.Failure -> CityListStates.Error(mutation.error)
        is CityListMutations.AddCityResult.Success -> {
            navigator follows Routes.AddCity
            previousState
        }
        else -> error("Unknown mutation $mutation")
    }


    private fun loadDatas(): Observable<Mutation> = service.loadMeteoCityList()
            .map<Mutation> { CityListMutations.LoadMeteoCityListResult.Success(it) }
            .onErrorReturn { CityListMutations.LoadMeteoCityListResult.Failure(it) }
            .startWith(CityListMutations.LoadMeteoCityListResult.InFlight)

    private fun addCity(): Observable<Mutation> = Observable.just(CityListMutations.AddCityResult.Success)
}