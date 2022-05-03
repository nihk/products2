plugins {
    `android-library`
    kotlin("android")
    kotlin("kapt")
    hilt
}

androidLibraryConfig {
    addSharedTestDirectory("sharedTest")
    withCompose()
}

dependencies {
    implementation(project(Modules.navigation))
    implementation(project(Modules.mvi))
    implementation(project(Modules.local))
    implementation(project(Modules.remote))
    implementation(project(Modules.logging))

    implementation(Dependencies.Dagger.runtime)
    implementation(Dependencies.Dagger.Hilt.runtime)
    implementation(Dependencies.Dagger.Hilt.Jetpack.navigation)
    implementation(Dependencies.Coil.compose)
    withCompose()

    testImplementation(project(Modules.test))
    testImplementation(Dependencies.Kotlin.coroutinesTest)
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.Moshi.runtime)
    testImplementation(Dependencies.Moshi.adapters)

    kapt(Dependencies.Dagger.compiler)
    kapt(Dependencies.Dagger.Hilt.compiler)
}
