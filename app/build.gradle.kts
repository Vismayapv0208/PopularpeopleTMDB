plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    ( "androidx.navigation.safeargs.kotlin")

}

android {
    namespace = "com.example.movie"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.movie"
        minSdk = 21
        targetSdk = 35
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
    implementation(libs.androidx.navigation.fragment)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.constraintlayout)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)
        implementation(libs.androidx.lifecycle.livedata.ktx)
        implementation(libs.kotlinx.coroutines.android)
        implementation(libs.retrofit)
        implementation(libs.converter.gson)
        implementation(libs.okhttp)
        implementation(libs.logging.interceptor)
        implementation(libs.androidx.recyclerview)
        implementation(libs.androidx.paging.runtime.ktx)
        implementation(libs.hilt.android)
        implementation(libs.androidx.fragment.ktx)
        implementation(libs.gson)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit.v115)
        androidTestImplementation(libs.androidx.espresso.core.v351)
        implementation(libs.retrofit)
        implementation(libs.converter.gson)
        implementation(libs.hilt.android.v2481)
        kapt(libs.hilt.compiler)
        implementation(libs.androidx.hilt.navigation.compose)
        implementation (libs.glide)
        kapt ("com.github.bumptech.glide:compiler:4.14.2")
}
