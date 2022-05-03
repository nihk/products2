package nick.template.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import nick.template.models.MainEffect
import nick.template.navigation.Navigator

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val navController: NavHostController = rememberNavController()
            val state by viewModel.states.collectAsState()

            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(navController = navController, startDestination = state.startDestination) {
                        state.screens.forEach { screen ->
                            composable(
                                route = screen.name,
                                arguments = screen.arguments,
                            ) { navBackStackEntry ->
                                screen.Content(arguments = navBackStackEntry.arguments)
                            }
                        }
                    }
                }

                LaunchedEffect(key1 = Unit) {
                    viewModel
                        .effects
                        .onEach { effect ->
                            when (effect) {
                                is MainEffect.NavigateEffect -> when (effect.command) {
                                    is Navigator.Command.Destination -> navController.navigate(effect.command.route)
                                    Navigator.Command.Pop -> navController.popBackStack()
                                }
                            }
                        }
                        .launchIn(this)
                }
            }
        }
    }
}
