package fr.juliendenadai.reactor.sample.addcity

import fr.juliendenadai.reactor.State

sealed class AddCityStates : State() {
    object Idle : AddCityStates()
    object Loading : AddCityStates()
    class Error(val error: Throwable) : AddCityStates()
}
