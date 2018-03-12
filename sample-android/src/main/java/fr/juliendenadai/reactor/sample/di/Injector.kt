package fr.juliendenadai.reactor.sample.di

import fr.juliendenadai.reactor.INavigator
import fr.juliendenadai.reactor.sample.Navigator
import fr.juliendenadai.reactor.sample.services.IMeteoService
import fr.juliendenadai.reactor.sample.services.MeteoServiceDummy

/**
 * Fake injection framework
 */
object Injector {
    val meteoService: IMeteoService by lazy { MeteoServiceDummy() }
    val navigator: INavigator by lazy { Navigator() }
}

inline fun <reified T> inject(): Lazy<T> = lazy {
    when {
        IMeteoService::class == T::class -> Injector.meteoService as T
        INavigator::class == T::class -> Injector.navigator as T
        else -> error("Unknown type ${T::class.java.simpleName}")
    }
}
