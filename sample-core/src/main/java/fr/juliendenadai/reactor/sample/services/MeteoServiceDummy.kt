package fr.juliendenadai.reactor.sample.services

import fr.juliendenadai.reactor.sample.pojo.MeteoForCity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.util.*
import java.util.concurrent.TimeUnit

class MeteoServiceDummy : IMeteoService {

    private var datas = listOf(MeteoForCity("Bordeaux", 23))

    private val meteoCityList: Subject<List<MeteoForCity>> by lazy { BehaviorSubject.createDefault(datas) }

    override fun addCity(city: CharSequence): Completable = Completable.create { emitter ->
        when {
            city.isEmpty() -> emitter.onError(Throwable("City must not be empty"))
            city.toString() in cities -> {
                datas += MeteoForCity(city.toString(), Random().nextInt(35))
                meteoCityList.onNext(datas)
                emitter.onComplete()
            }
            else -> emitter.onError(Throwable("City not found"))
        }
    }

    override fun loadMeteoCityList(): Observable<List<MeteoForCity>> = meteoCityList.delay(2, TimeUnit.SECONDS, Schedulers.io())

    companion object {
        private val cities = listOf("Paris", "Toulouse", "Marseilles", "Rennes")
    }
}
