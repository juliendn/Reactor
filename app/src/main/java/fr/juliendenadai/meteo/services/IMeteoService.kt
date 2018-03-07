package fr.juliendenadai.meteo.services

import fr.juliendenadai.meteo.pojo.MeteoForCity
import io.reactivex.Completable
import io.reactivex.Single

interface IMeteoService {

    fun addCity(city: CharSequence): Completable

    fun loadMeteoCityList(): Single<List<MeteoForCity>>
}
