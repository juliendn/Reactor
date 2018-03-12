package fr.juliendenadai.reactor.sample

import fr.juliendenadai.reactor.sample.citylist.CityListReactor
import fr.juliendenadai.reactor.sample.citylist.SwingView
import fr.juliendenadai.reactor.sample.services.MeteoServiceDummy

fun main(args: Array<String>) {
    val service = MeteoServiceDummy()
    val navigator = Navigator()
    val reactor = CityListReactor(service, navigator)
    SwingView(reactor, navigator).frame.isVisible = true
}

