package nick.template.navigation

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Singleton
class Navigator @Inject constructor() {
    private val commands = MutableSharedFlow<Command>()

    fun commands() = commands.asSharedFlow()

    suspend fun process(command: Command) {
        commands.emit(command)
    }

    sealed class Command {
        object Pop : Command()
        data class Destination(val route: String) : Command()
    }
}
