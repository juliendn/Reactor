package fr.juliendenadai.meteo.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import fr.juliendenadai.meteo.addcity.AddCityViewModel
import fr.juliendenadai.meteo.citylist.CityListViewModel
import java.lang.IllegalArgumentException

object ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            CityListViewModel::class.java.isAssignableFrom(modelClass) -> CityListViewModel(Injector.meteoService) as T
            AddCityViewModel::class.java.isAssignableFrom(modelClass) -> AddCityViewModel(Injector.meteoService) as T
            else -> throw IllegalArgumentException("No constructor for class ${modelClass.simpleName}")
        }
    }

    inline fun <reified T : ViewModel> provideLazily(activity: FragmentActivity): Lazy<T> = lazy {
        ViewModelProviders.of(activity, this).get(T::class.java)
    }
}
