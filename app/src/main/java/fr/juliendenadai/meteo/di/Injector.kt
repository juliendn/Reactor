package fr.juliendenadai.meteo.di

import fr.juliendenadai.meteo.services.IMeteoService
import fr.juliendenadai.meteo.services.MeteoServiceDummy

/**
 * Fake injection framework
 */
object Injector {
    val meteoService: IMeteoService by lazy { MeteoServiceDummy() }
}
