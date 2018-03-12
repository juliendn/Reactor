package fr.juliendenadai.reactor.sample.addcity

import fr.juliendenadai.reactor.Action

sealed class AddCityActions : Action() {
    class SearchCity(val city: CharSequence) : AddCityActions()
}
