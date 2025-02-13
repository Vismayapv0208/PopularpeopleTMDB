plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // ... other plugins ...
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


//    dependencies {
        // Core
//        implementation("androidx.core:core-ktx:1.12.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.11.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")

        // ViewModel and LiveData
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

        // Coroutines
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

        // Retrofit
        implementation(libs.retrofit)
        implementation(libs.converter.gson)
        implementation(libs.okhttp)
        implementation(libs.logging.interceptor)

        // RecyclerView
        implementation("androidx.recyclerview:recyclerview:1.3.2")

        // Paging 3
        implementation("androidx.paging:paging-runtime-ktx:3.2.1")

        // Dependency Injection (Hilt)
        implementation(libs.hilt.android)
//        kapt(libs.hilt.android.compiler)

        // Fragment
        implementation("androidx.fragment:fragment-ktx:1.6.2")

        // Gson
        implementation("com.google.code.gson:gson:2.10.1")

        // Testing (optional)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit.v115)
        androidTestImplementation(libs.androidx.espresso.core.v351)
//    dependencies {/
        // ... other dependencies ...
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        // Hilt
        implementation("com.google.dagger:hilt-android:2.48.1")
        kapt("com.google.dagger:hilt-compiler:2.48.1")
        implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
//    }

//    dependencies {
        // Add Glide dependency
        implementation ("com.github.bumptech.glide:glide:4.14.2") // or the latest version
        kapt ("com.github.bumptech.glide:compiler:4.14.2") // Glide annotation processor
        implementation( "androidx.navigation:navigation-fragment-ktx:2.5.0")
        implementation( "androidx.navigation:navigation-ui-ktx:2.5.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
}
// Hilt

//}