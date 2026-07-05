plugins {
    id("com.android.application")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.ismail.cu"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.ismail.cu"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // androidx common dependencies
    api(libs.bundles.androidx.common.dependencies)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)

    // gson
    implementation(libs.gson)

    // tests dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidx.test.dependencies)
}