package nick.template.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument

interface Screen {
    val name: String
    val arguments: List<NamedNavArgument> get() = emptyList()

    @Composable
    fun Content(arguments: Bundle?)
}
