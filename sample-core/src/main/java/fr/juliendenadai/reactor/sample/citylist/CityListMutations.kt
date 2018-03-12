package fr.juliendenadai.reactor.sample.citylist

import fr.juliendenadai.reactor.Mutation
import fr.juliendenadai.reactor.sample.pojo.MeteoForCity

sealed class CityListMutations : Mutation() {
    sealed class LoadMeteoCityListResult : CityListMutations() {
        data class Success(val meteoCity: List<MeteoForCity>) : LoadMeteoCityListResult()
        data class Failure(val error: Throwable) : LoadMeteoCityListResult()
        object InFlight : LoadMeteoCityListResult()
    }

    sealed class AddCityResult : CityListMutations() {
        object Success : AddCityResult()
    }
}
