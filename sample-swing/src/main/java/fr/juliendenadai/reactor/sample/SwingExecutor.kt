package fr.juliendenadai.reactor.sample

import java.util.concurrent.Executor
import javax.swing.SwingUtilities

object SwingExecutor : Executor {

    override fun execute(command: Runnable) {
        if (SwingUtilities.isEventDispatchThread()) {
            command.run()
        } else {
            SwingUtilities.invokeLater(command)
        }
    }
}