package fr.juliendenadai.reaktor.sample

import fr.juliendenadai.reaktor.INavigator
import fr.juliendenadai.reaktor.Route
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class Navigator : INavigator {

    override val navigation: Subject<Route> = PublishSubject.create()

    override fun follows(route: Route) {
        navigation.onNext(route)
    }
}
