package fr.juliendenadai.reactor

import java.util.*

/**
 * Tool to store Action and State played
 * Objective is to be able to rewind and replay scenario
 */
object History {

    private val actionStack = Stack<Action>()
    private val stateStack = Stack<State>()

    /**
     * Store an Action
     * @param action the action to store
     */
    fun pushUiEvent(action: Action) {
        actionStack.push(action)
    }

    /**
     * Store a State
     * @param state the state to store
     */
    fun pushUiModel(state: State) {
        stateStack.push(state)
    }
}
