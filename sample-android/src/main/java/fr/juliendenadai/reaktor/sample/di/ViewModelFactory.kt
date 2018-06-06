package fr.juliendenadai.reaktor.sample.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import fr.juliendenadai.reaktor.IHistory
import fr.juliendenadai.reaktor.INavigator
import fr.juliendenadai.reaktor.ReaktorActivity
import fr.juliendenadai.reaktor.sample.addcity.AddCityReaktor
import fr.juliendenadai.reaktor.sample.addcity.AddCityViewModel
import fr.juliendenadai.reaktor.sample.citylist.CityListReaktor
import fr.juliendenadai.reaktor.sample.citylist.CityListViewModel
import fr.juliendenadai.reaktor.sample.services.IMeteoService
import java.lang.IllegalArgumentException

object ViewModelFactory : ViewModelProvider.Factory {

    private val service by inject<IMeteoService>()
    private val navigator by inject<INavigator>()
    private val history by inject<IHistory>()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            CityListViewModel::class.java.isAssignableFrom(modelClass) -> CityListViewModel(
                CityListReaktor(service, navigator, history)
            ) as T
            AddCityViewModel::class.java.isAssignableFrom(modelClass) -> AddCityViewModel(
                AddCityReaktor(service, navigator, history)
            ) as T
            else -> throw IllegalArgumentException("No constructor for class ${modelClass.simpleName}")
        }
    }
}

inline fun <reified T : ViewModel> ReaktorActivity.lazyViewModel(): Lazy<T> = lazy {
    ViewModelProviders.of(this, ViewModelFactory).get(T::class.java)
}
