
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}
val apiKey = project.findProperty("API_KEY") as String? ?: ""
android {
    namespace = "com.example.frontpage"
    compileSdk = 36
    // ✅ correct
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.example.frontpage"
        minSdk = 30
        targetSdk = 36
        // ✅ realistic
        // ✅ correct
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

            buildConfigField("String", "API_KEY", "\"$apiKey\"")

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
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}


dependencies {


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.cardview)
    implementation(libs.firebase.database)


    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    // Google Maps
    implementation("com.google.android.gms:play-services-maps:18.1.0")
// Fused Location Provider (current location)
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation(libs.legacy.support.v4)
    implementation(libs.viewpager2)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.espresso.web)
    testImplementation(libs.junit)
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
