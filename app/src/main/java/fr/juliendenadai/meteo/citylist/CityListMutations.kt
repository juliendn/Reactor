package fr.juliendenadai.meteo.citylist

import fr.juliendenadai.meteo.pojo.MeteoForCity
import fr.juliendenadai.reactor.Mutation

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
