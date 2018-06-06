package fr.juliendenadai.reaktor.sample.citylist

import fr.juliendenadai.reaktor.Mutation
import fr.juliendenadai.reaktor.sample.pojo.MeteoForCity

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
