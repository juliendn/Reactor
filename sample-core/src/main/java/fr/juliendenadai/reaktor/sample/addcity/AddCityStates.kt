package fr.juliendenadai.reaktor.sample.addcity

import fr.juliendenadai.reaktor.State

sealed class AddCityStates : State() {
    object Idle : AddCityStates()
    object Loading : AddCityStates()
    class Error(val error: Throwable) : AddCityStates()
}
