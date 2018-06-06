@file:JvmName("Main")

package fr.juliendenadai.reaktor.sample

import fr.juliendenadai.reaktor.History
import fr.juliendenadai.reaktor.sample.citylist.CityListReaktor
import fr.juliendenadai.reaktor.sample.citylist.SwingView
import fr.juliendenadai.reaktor.sample.services.MeteoServiceDummy

fun main(args: Array<String>) {

    val service = MeteoServiceDummy()
    val navigator = Navigator()
    val history = History(listOf(SystemOutDebugger()))

    val reaktor = CityListReaktor(service, navigator, history)
    SwingView(reaktor, navigator).frame.isVisible = true
}
