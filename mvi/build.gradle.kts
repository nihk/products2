plugins {
    `android-library`
    kotlin("android")
}

androidLibraryConfig()

dependencies {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.Lifecycle.viewModel)
}
