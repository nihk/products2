package nick.template.models

import nick.template.navigation.Navigator
import nick.template.navigation.Screen

sealed class MainEvent {
    object LoadScreenEvent : MainEvent()
}

sealed class MainResult {
    data class NavigateResult(val command: Navigator.Command) : MainResult()
}

sealed class MainEffect {
    data class NavigateEffect(val command: Navigator.Command) : MainEffect()
}

data class MainState(
    val startDestination: String,
    val screens: Set<Screen>
)
