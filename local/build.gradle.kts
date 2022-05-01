plugins {
    `android-library`
    kotlin("android")
    ksp
}

androidLibraryConfig()

dependencies {
    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.roomKtx)

    androidTestImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.ArchCore.testing)
    androidTestImplementation(Dependencies.Kotlin.coroutinesTest)
    androidTestImplementation(Dependencies.Room.testing)
    defaultAndroidTestDependencies()

    kspAndroidTest(Dependencies.Room.compiler)
}
