plugins {
    `android-library`
    kotlin("android")
    ksp
}

androidLibraryConfig()

dependencies {
    implementation(project(Modules.logging))

    implementation(Dependencies.Retrofit.runtime)
    implementation(Dependencies.Retrofit.moshi)
    implementation(Dependencies.OkHttp.loggingInterceptor)
    implementation(Dependencies.Moshi.runtime)
    implementation(Dependencies.Moshi.adapters)
    implementation(Dependencies.Dagger.runtime)
    implementation(Dependencies.Dagger.Hilt.runtime)

    ksp(Dependencies.Moshi.kotlinCodegen)
}
