plugins {
    `android-application`
    kotlin("android")
    kotlin("kapt")
    hilt
    ksp
}

androidAppConfig {
    defaultConfig {
        applicationId = "nick.products2"
        versionCode = 1
        versionName = "1.0"

        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = Versions.compose
        }
    }
}

withKtlint()

dependencies {
    implementation(project(Modules.list))

    implementation(Dependencies.multidex)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.Activity.runtime)
    implementation(Dependencies.Activity.compose)
    implementation(Dependencies.vectorDrawable)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.material)
    implementation(Dependencies.photoView)
    implementation(Dependencies.Retrofit.runtime)
    implementation(Dependencies.Retrofit.moshi)
    implementation(Dependencies.Moshi.runtime)
    implementation(Dependencies.Moshi.adapters)
    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.roomKtx)
    implementation(Dependencies.OkHttp.loggingInterceptor)
    implementation(Dependencies.coil)
    implementation(Dependencies.Dagger.runtime)
    implementation(Dependencies.Dagger.Hilt.runtime)
    implementation(Dependencies.Lifecycle.runtime)
    implementation(Dependencies.Lifecycle.compose)
    withCompose()


//    debugImplementation(Dependencies.leakCanary)

    testImplementation(Dependencies.junit)
    defaultAndroidTestDependencies()

    ksp(Dependencies.Moshi.kotlinCodegen)
    ksp(Dependencies.Room.compiler)
    kapt(Dependencies.Dagger.compiler)
    kapt(Dependencies.Dagger.Hilt.compiler)
}
