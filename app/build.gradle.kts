plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.ksp) // agregar
    alias(libs.plugins.room) // aplicar aquí el alias del plugin
}

// --- BLOQUE OBLIGATORIO PARA EL PLUGIN DE ROOM ---
room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "com.example.smartvolt"
    compileSdk {
        version = release(37)
    }
    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.room.runtime)
    implementation(libs.room.ktx) // Soporte para Corrutinas y Flow con Room
    ksp(libs.room.compiler)       // Procesador de anotaciones usando KSP
    // --- VIEWMODEL PARA COMPOSE ---
    implementation(libs.compose.viewmodel) // Permite usar 'viewModel()' en Compose
    // --- ICONOS EXTENDIDOS DE MATERIAL ---
    implementation(libs.compose.icons.extended)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.navigation.compose)
    //google y ubi del usuario
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
    // Para usar .await() con tasks de Google Play Services en coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.0")
    // Mapbox Compose (o la versión que estés utilizando en tu proyecto)
    val mapboxVersion = "11.2.0"
    implementation("com.mapbox.maps:android:${mapboxVersion}")
    implementation("com.mapbox.extension:maps-compose:${mapboxVersion}")
}
