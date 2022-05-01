plugins {
    `android-library`
    kotlin("android")
}

androidLibraryConfig {
    withCompose()
}

dependencies {
    api(Dependencies.Navigation.runtime)
    api(Dependencies.Navigation.compose)
    withCompose()
}
