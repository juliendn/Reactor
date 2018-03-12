package fr.juliendenadai.reactor.sample.addcity

import fr.juliendenadai.reactor.*
import fr.juliendenadai.reactor.sample.Routes
import fr.juliendenadai.reactor.sample.services.IMeteoService
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class AddCityReactor(
        private val service: IMeteoService,
        private val navigator: INavigator
) : Reactor() {

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
