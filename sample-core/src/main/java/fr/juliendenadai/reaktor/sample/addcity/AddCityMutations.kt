package fr.juliendenadai.reaktor.sample.addcity

import fr.juliendenadai.reaktor.Mutation

sealed class AddCityMutations : Mutation() {
    object InFlight : AddCityMutations()
    object Success : AddCityMutations()
    class Failure(val error: Throwable) : AddCityMutations()
}
