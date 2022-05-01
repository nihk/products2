plugins {
    `android-library`
    kotlin("android")
}

androidLibraryConfig()

dependencies {
    api(Dependencies.Navigation.runtime)
    api(Dependencies.Navigation.compose)
    withCompose()
}
