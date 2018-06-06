package fr.juliendenadai.reaktor.sample

import fr.juliendenadai.reaktor.Route

sealed class Routes : Route() {
    object AddCity : Routes()
    object CityList : Routes()
}
