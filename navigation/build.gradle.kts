plugins {
    `android-library`
    kotlin("android")
}

androidLibraryConfig()

dependencies {
    implementation(Dependencies.Navigation.runtime)
    implementation(Dependencies.Navigation.compose)
    withCompose()
}
