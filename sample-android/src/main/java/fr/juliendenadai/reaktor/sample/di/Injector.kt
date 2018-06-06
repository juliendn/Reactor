package fr.juliendenadai.reaktor.sample.di

import fr.juliendenadai.reaktor.History
import fr.juliendenadai.reaktor.IHistory
import fr.juliendenadai.reaktor.INavigator
import fr.juliendenadai.reaktor.sample.debug.LogcatDebug
import fr.juliendenadai.reaktor.sample.debug.WebsocketDebug
import fr.juliendenadai.reaktor.sample.Navigator
import fr.juliendenadai.reaktor.sample.services.IMeteoService
import fr.juliendenadai.reaktor.sample.services.MeteoServiceDummy

/**
 * Fake injection framework
 */
object Injector {
    val meteoService: IMeteoService by lazy { MeteoServiceDummy() }
    val navigator: INavigator by lazy { Navigator() }
    val history: IHistory by lazy {
        History(listOf(
                LogcatDebug("METEO"),
                WebsocketDebug(8080, "/ws")
        ))
    }
}

inline fun <reified T> inject(): Lazy<T> = lazy {
    when {
        IMeteoService::class == T::class -> Injector.meteoService as T
        INavigator::class == T::class -> Injector.navigator as T
        IHistory::class == T::class -> Injector.history as T
        else -> error("Unknown type ${T::class.java.simpleName}")
    }
}
