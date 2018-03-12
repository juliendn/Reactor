package fr.juliendenadai.reactor.sample

import fr.juliendenadai.reactor.INavigator
import fr.juliendenadai.reactor.Route
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class Navigator : INavigator {

    override val navigation: Subject<Route> = PublishSubject.create()

    override fun follows(route: Route) {
        navigation.onNext(route)
    }
}