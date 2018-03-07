package fr.juliendenadai.meteo.services

import fr.juliendenadai.meteo.pojo.MeteoForCity
import io.reactivex.Completable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class MeteoServiceDummy : IMeteoService {

    override fun addCity(city: CharSequence): Completable = Completable.create { emitter ->
        when {
            city.isEmpty() -> emitter.onError(Throwable("City must not be empty"))
            !"Paris".equals(city.toString(), true) -> emitter.onError(Throwable("City not found"))
            else -> emitter.onComplete()
        }
    }

    override fun loadMeteoCityList(): Single<List<MeteoForCity>> = getDatas().delay(2, TimeUnit.SECONDS)

    private fun getDatas(): Single<List<MeteoForCity>> {
        return Single.just(listOf(
                MeteoForCity("Toulouse", 18F),
                MeteoForCity("Toulouse", 18F),
                MeteoForCity("Toulouse", 18F),
                MeteoForCity("Toulouse", 18F),
                MeteoForCity("Toulouse", 18F),
                MeteoForCity("Toulouse", 18F)
        ))
    }
}
