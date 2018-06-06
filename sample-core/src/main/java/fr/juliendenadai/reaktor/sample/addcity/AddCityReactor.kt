package fr.juliendenadai.reaktor.sample.addcity

import fr.juliendenadai.reaktor.*
import fr.juliendenadai.reaktor.sample.Routes
import fr.juliendenadai.reaktor.sample.services.IMeteoService
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class AddCityReaktor(
        private val service: IMeteoService,
        private val navigator: INavigator,
        history: IHistory
) : Reaktor(history) {

    override val initialState: State = AddCityStates.Idle

    override fun mutate(action: Action): Observable<Mutation> = when (action) {
        is AddCityActions.SearchCity -> addCity(action.city)
        else -> error("Action unknown $action")
    }

    override fun reduce(previousState: State, mutation: Mutation): State = when (mutation) {
        is AddCityMutations.InFlight -> AddCityStates.Loading
        is AddCityMutations.Success -> {
            navigator follows Routes.CityList
            previousState
        }
        is AddCityMutations.Failure -> AddCityStates.Error(mutation.error)
        else -> error("Mutation unknown $mutation")
    }

    private fun addCity(city: CharSequence): Observable<Mutation> = service.addCity(city)
            .andThen(Observable.just<Mutation>(AddCityMutations.Success))
            .onErrorReturn { AddCityMutations.Failure(it) }
            .delay(2, TimeUnit.SECONDS)
            .startWith(AddCityMutations.InFlight)
}
