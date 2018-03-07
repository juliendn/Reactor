package fr.juliendenadai.meteo.addcity

import fr.juliendenadai.reactor.State

sealed class AddCityStates : State() {
    object Idle : AddCityStates()
    object Loading : AddCityStates()
    object Success : AddCityStates()
    class Error(val error: Throwable) : AddCityStates()
}
