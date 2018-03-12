package fr.juliendenadai.reactor.sample

import fr.juliendenadai.reactor.Route

sealed class Routes : Route() {
    object AddCity : Routes()
    object CityList : Routes()
}