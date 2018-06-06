package fr.juliendenadai.reaktor.sample.services

import fr.juliendenadai.reaktor.sample.pojo.MeteoForCity
import io.reactivex.Completable
import io.reactivex.Observable

interface IMeteoService {

    fun addCity(city: CharSequence): Completable

    fun loadMeteoCityList(): Observable<List<MeteoForCity>>
}
