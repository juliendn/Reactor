package fr.juliendenadai.reactor.sample.addcity

import fr.juliendenadai.reactor.Mutation

sealed class AddCityMutations : Mutation() {
    object InFlight : AddCityMutations()
    object Success : AddCityMutations()
    class Failure(val error: Throwable) : AddCityMutations()
}
