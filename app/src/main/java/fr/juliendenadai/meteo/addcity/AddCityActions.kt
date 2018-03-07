package fr.juliendenadai.meteo.addcity

import fr.juliendenadai.reactor.Action

sealed class AddCityActions : Action() {
    class SearchCity(val city: CharSequence) : AddCityActions()
}
