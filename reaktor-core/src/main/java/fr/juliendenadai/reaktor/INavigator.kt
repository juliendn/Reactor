package fr.juliendenadai.reaktor

import io.reactivex.Observable

interface INavigator {

    val navigation: Observable<Route>

    infix fun follows(route: Route)
}
