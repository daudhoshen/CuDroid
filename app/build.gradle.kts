plugins {
    id("com.android.application")
    //id("kotlin-parcelize")
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
            //noinspection NotShrinkingResources
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
        sourceCompatibility = JavaVersion.toVersion(libs.versions.android.targetJvm.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.android.targetJvm.get())
    }
    buildFeatures {
        viewBinding = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(
            org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget(
                libs.versions.android.targetJvm.get()
            )
        )
    }
}

dependencies {
    // androidx common dependencies
    api(libs.androidx.coreKtx)
    api(libs.androidx.appcompat)
    api(libs.android.material)
    api(libs.androidx.constraintlayout)
    api(libs.androidx.lifecycle.runtime)
    api(libs.androidx.lifecycle.viewmodel)
    api(libs.androidx.navigation.runtime)
    api(libs.androidx.navigation.ui.ktx)
    api(libs.androidx.navigation.fragment)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)

    // gson
    implementation(libs.gson)
}