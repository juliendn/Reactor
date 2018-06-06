package fr.juliendenadai.reaktor.sample.citylist

import fr.juliendenadai.reaktor.Action

sealed class CityListActions : Action() {
    object AddCity : CityListActions()
    object LoadMeteoCityList : CityListActions()
}
