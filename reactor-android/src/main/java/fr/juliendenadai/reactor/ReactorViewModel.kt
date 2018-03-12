package fr.juliendenadai.reactor

import android.arch.lifecycle.ViewModel

/**
 * Base ViewModel
 */
open class ReactorViewModel(reactor: IReactor) : ViewModel(), IReactor by reactor
