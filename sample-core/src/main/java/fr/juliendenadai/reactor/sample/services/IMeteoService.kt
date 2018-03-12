package fr.juliendenadai.reactor.sample.services

import fr.juliendenadai.reactor.sample.pojo.MeteoForCity
import io.reactivex.Completable
import io.reactivex.Observable

interface IMeteoService {

    fun addCity(city: CharSequence): Completable

    fun loadMeteoCityList(): Observable<List<MeteoForCity>>
}
