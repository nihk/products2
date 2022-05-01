import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.defaultAndroidTestDependencies() {
    "androidTestImplementation"(Dependencies.Espresso.core)
    "androidTestImplementation"(Dependencies.Espresso.contrib)
    "androidTestImplementation"(Dependencies.AndroidTest.core)
    "androidTestImplementation"(Dependencies.AndroidTest.coreKtx)
    "androidTestImplementation"(Dependencies.AndroidTest.extJunit)
    "androidTestImplementation"(Dependencies.AndroidTest.runner)
    "androidTestImplementation"(Dependencies.AndroidTest.rules)
}

fun DependencyHandlerScope.withCompose() {
    "implementation"(Dependencies.Compose.compiler)
    "implementation"(Dependencies.Compose.ui)
    "implementation"(Dependencies.Compose.uiTooling)
    "implementation"(Dependencies.Compose.animation)
    "implementation"(Dependencies.Compose.runtime)
    "implementation"(Dependencies.Compose.foundation)
    "implementation"(Dependencies.Compose.material)
    "implementation"(Dependencies.Compose.materialIconsExtended)
}
