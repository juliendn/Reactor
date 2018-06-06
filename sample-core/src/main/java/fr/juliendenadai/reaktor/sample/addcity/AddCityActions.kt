package fr.juliendenadai.reaktor.sample.addcity

import fr.juliendenadai.reaktor.Action

sealed class AddCityActions : Action() {
    class SearchCity(val city: CharSequence) : AddCityActions()
}
