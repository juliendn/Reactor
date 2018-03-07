package fr.juliendenadai.meteo.citylist

import fr.juliendenadai.meteo.services.IMeteoService
import fr.juliendenadai.reactor.Action
import fr.juliendenadai.reactor.Mutation
import fr.juliendenadai.reactor.MviBaseViewModel
import fr.juliendenadai.reactor.State
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class CityListViewModel(
        private val service: IMeteoService
) : MviBaseViewModel() {

    override val initialState = CityListStates.Idle

    override fun mutate(action: Action): Observable<Mutation> = when (action) {
        is CityListActions.LoadMeteoCityList -> loadDatas()
        is CityListActions.AddCity -> addCity()
        else -> error("Unknown action $action")
    }

    override fun reduce(state: State, mutation: Mutation): State {
        return when (mutation) {
            is CityListMutations.LoadMeteoCityListResult.InFlight -> CityListStates.Loading
            is CityListMutations.LoadMeteoCityListResult.Success -> CityListStates.Loaded(mutation.meteoCity)
            is CityListMutations.LoadMeteoCityListResult.Failure -> CityListStates.Error(mutation.error)
            is CityListMutations.AddCityResult.Success -> CityListStates.NavigateToAddCity
            else -> error("Unknown mutation $mutation")
        }
    }

    private fun loadDatas(): Observable<Mutation> {
        return service.loadMeteoCityList()
                .toObservable()
                .map<Mutation> { CityListMutations.LoadMeteoCityListResult.Success(it) }
                .onErrorReturn { CityListMutations.LoadMeteoCityListResult.Failure(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .startWith(CityListMutations.LoadMeteoCityListResult.InFlight)
    }

    private fun addCity(): Observable<Mutation> {
        return Observable.just<Mutation>(CityListMutations.AddCityResult.Success)
                .observeOn(AndroidSchedulers.mainThread())
    }
}
