package fr.juliendenadai.reaktor

import android.arch.lifecycle.ViewModel

/**
 * Base ViewModel
 */
open class ReaktorViewModel(val reactor: IReaktor) : ViewModel(), IReaktor by reactor
