package fr.juliendenadai.meteo.citylist

import fr.juliendenadai.meteo.pojo.MeteoForCity
import fr.juliendenadai.reactor.State

sealed class CityListStates : State() {
    object Idle : CityListStates()
    object Loading : CityListStates()
    class Loaded(val datas: List<MeteoForCity>) : CityListStates()
    class Error(val error: Throwable) : CityListStates()
    object NavigateToAddCity : CityListStates()
}
