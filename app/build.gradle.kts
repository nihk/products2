plugins {
    `android-application`
    `kotlin-android`
    kapt
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

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
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
    implementation(project(Modules.navigation))
    implementation(project(Modules.mvi))

    implementation(Dependencies.multidex)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.Activity.runtime)
    implementation(Dependencies.Activity.compose)
    implementation(Dependencies.Lifecycle.runtime)
    implementation(Dependencies.Lifecycle.compose)
    implementation(Dependencies.Dagger.runtime)
    implementation(Dependencies.Dagger.Hilt.runtime)
    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.roomKtx)
    implementation(Dependencies.Navigation.runtime)
    implementation(Dependencies.Navigation.compose)
    implementation(Dependencies.Dagger.Hilt.runtime)
    implementation(Dependencies.Dagger.Hilt.Jetpack.navigation)
    implementation(Dependencies.Coil.compose)
    withCompose()

    debugImplementation(Dependencies.leakCanary)

    testImplementation(Dependencies.junit)
    defaultAndroidTestDependencies()

    kapt(Dependencies.Dagger.compiler)
    kapt(Dependencies.Dagger.Hilt.compiler)
    ksp(Dependencies.Room.compiler)
}
