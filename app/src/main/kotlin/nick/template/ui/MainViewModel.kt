package nick.template.ui

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import nick.template.list.ui.productListName
import nick.template.models.MainEffect
import nick.template.models.MainEvent
import nick.template.models.MainResult
import nick.template.models.MainState
import nick.template.mvi.MviViewModel
import nick.template.navigation.Navigator
import nick.template.navigation.Screen

@HiltViewModel
class MainViewModel @Inject constructor(
    screens: Set<@JvmSuppressWildcards Screen>,
    private val navigator: Navigator
) : MviViewModel<MainEvent, MainResult, MainState, MainEffect>(
    MainState(
        startDestination = productListName(),
        screens = screens
    )
) {
    override fun onSubscription() {
        processEvent(MainEvent.LoadScreenEvent)
    }

    override fun Flow<MainEvent>.toResults(): Flow<MainResult> {
        return merge(
            filterIsInstance<MainEvent.LoadScreenEvent>().onLoadScreen()
        )
    }

    private fun Flow<MainEvent.LoadScreenEvent>.onLoadScreen(): Flow<MainResult> {
        return flatMapLatest { navigator.commands() }
            .map(MainResult::NavigateResult)
    }

    override fun Flow<MainResult>.toEffects(): Flow<MainEffect> {
        return merge(
            filterIsInstance<MainResult.NavigateResult>().toNavigateEffect()
        )
    }

    private fun Flow<MainResult.NavigateResult>.toNavigateEffect(): Flow<MainEffect> {
        return mapLatest { result -> MainEffect.NavigateEffect(result.command) }
    }

    override fun MainResult.reduce(state: MainState): MainState {
        return when (this) {
            else -> state
        }
    }
}
