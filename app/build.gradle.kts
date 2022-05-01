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
    implementation(project(Modules.detail))
    implementation(project(Modules.logging))
    implementation(project(Modules.remote))
    implementation(project(Modules.local))

    implementation(Dependencies.multidex)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.Activity.runtime)
    implementation(Dependencies.Activity.compose)
    implementation(Dependencies.Dagger.runtime)
    implementation(Dependencies.Dagger.Hilt.runtime)
    withCompose()


//    debugImplementation(Dependencies.leakCanary)

    testImplementation(Dependencies.junit)
    defaultAndroidTestDependencies()

    kapt(Dependencies.Dagger.compiler)
    kapt(Dependencies.Dagger.Hilt.compiler)
}
