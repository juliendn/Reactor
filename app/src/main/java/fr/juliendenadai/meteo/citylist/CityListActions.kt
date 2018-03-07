package fr.juliendenadai.meteo.citylist

import fr.juliendenadai.reactor.Action

sealed class CityListActions : Action() {
    object AddCity : CityListActions()
    object LoadMeteoCityList : CityListActions()
}
