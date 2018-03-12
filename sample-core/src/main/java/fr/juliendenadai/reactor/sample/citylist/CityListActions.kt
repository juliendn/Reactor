package fr.juliendenadai.reactor.sample.citylist

import fr.juliendenadai.reactor.Action

sealed class CityListActions : Action() {
    object AddCity : CityListActions()
    object LoadMeteoCityList : CityListActions()
}
