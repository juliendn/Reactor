package fr.juliendenadai.reactor.sample.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import fr.juliendenadai.reactor.INavigator
import fr.juliendenadai.reactor.ReactorActivity
import fr.juliendenadai.reactor.sample.addcity.AddCityReactor
import fr.juliendenadai.reactor.sample.addcity.AddCityViewModel
import fr.juliendenadai.reactor.sample.citylist.CityListReactor
import fr.juliendenadai.reactor.sample.citylist.CityListViewModel
import fr.juliendenadai.reactor.sample.services.IMeteoService
import java.lang.IllegalArgumentException

object ViewModelFactory : ViewModelProvider.Factory {

    private val service by inject<IMeteoService>()
    private val navigator by inject<INavigator>()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            CityListViewModel::class.java.isAssignableFrom(modelClass) -> CityListViewModel(CityListReactor(service, navigator)) as T
            AddCityViewModel::class.java.isAssignableFrom(modelClass) -> AddCityViewModel(AddCityReactor(service, navigator)) as T
            else -> throw IllegalArgumentException("No constructor for class ${modelClass.simpleName}")
        }
    }
}

inline fun <reified T : ViewModel> ReactorActivity.lazyViewModel(): Lazy<T> = lazy {
    ViewModelProviders.of(this, ViewModelFactory).get(T::class.java)
}
