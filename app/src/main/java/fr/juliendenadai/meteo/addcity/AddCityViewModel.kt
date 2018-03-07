package fr.juliendenadai.meteo.addcity

import fr.juliendenadai.meteo.services.IMeteoService
import fr.juliendenadai.reactor.Action
import fr.juliendenadai.reactor.Mutation
import fr.juliendenadai.reactor.MviBaseViewModel
import fr.juliendenadai.reactor.State
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class AddCityViewModel(private val service: IMeteoService) : MviBaseViewModel() {

    override val initialState = AddCityStates.Idle

    override fun mutate(action: Action): Observable<Mutation> = when (action) {
        is AddCityActions.SearchCity -> addCity(action.city)
        else -> error("Action unknown $action")
    }


    override fun reduce(state: State, mutation: Mutation): State = when (mutation) {
        is AddCityMutations.InFlight -> AddCityStates.Loading
        is AddCityMutations.Success -> AddCityStates.Success
        is AddCityMutations.Failure -> AddCityStates.Error(mutation.error)
        else -> error("Mutation unknown $mutation")
    }

    private fun addCity(city: CharSequence): Observable<Mutation> {
        return service.addCity(city)
                .andThen(Observable.just<Mutation>(AddCityMutations.Success))
                .onErrorReturn { AddCityMutations.Failure(it) }
                .delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .startWith(AddCityMutations.InFlight)
    }
}
