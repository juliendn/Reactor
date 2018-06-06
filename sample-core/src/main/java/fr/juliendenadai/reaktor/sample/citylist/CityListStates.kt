package fr.juliendenadai.reaktor.sample.citylist

import fr.juliendenadai.reaktor.sample.pojo.MeteoForCity
import fr.juliendenadai.reaktor.State

sealed class CityListStates : State() {
    object Idle : CityListStates()
    object Loading : CityListStates()
    class Loaded(val datas: List<MeteoForCity>) : CityListStates()
    class Error(val error: Throwable) : CityListStates()
}
