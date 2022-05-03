plugins {
    `android-library`
    kotlin("android")
}

androidLibraryConfig()

dependencies {
    implementation(Dependencies.Kotlin.coroutinesTest)
    implementation(Dependencies.junit)
}
