plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    //    THIS IS TO USE RAELM DATABASE :
    id("io.realm.kotlin")
    //   FIREBASE:
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.xmltools"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.xmltools"
        minSdk = 28
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.animation.core.lint)
    implementation(libs.androidx.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //    THIS IS TO USE RAELM DATABASE :
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation("io.realm.kotlin:library-base:3.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    //  TGHIS IS TO USE VIEWMODEL PLUGIN :
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    //  Firebase:
    implementation(platform("com.google.firebase:firebase-bom:34.3.0"))
    implementation("com.google.firebase:firebase-analytics")
    //  Firebase FireStore :
    implementation("com.google.firebase:firebase-firestore")
    //  Login using Email And Password in Firebase:
    implementation("com.google.firebase:firebase-auth")
    //Login using Gmail :
    implementation("androidx.credentials:credentials:1.3.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
    //  Navigation :
    implementation("androidx.navigation:navigation-compose:2.8.6")
    // Navigation with Animation
    implementation("com.google.accompanist:accompanist-navigation-animation:0.36.0")
    //  Window Size :
    implementation("androidx.compose.material3:material3-window-size-class:1.2.1")
    // to change systemNavigation Bar Colors :
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.34.0")
    implementation("com.guolindev.permissionx:permissionx:1.7.1")
    // to use Lottie animation :
    implementation("com.airbnb.android:lottie-compose:6.3.0")
}