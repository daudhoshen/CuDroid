plugins {
    alias(libs.plugins.android.application) apply false

    alias(libs.plugins.gms.googleServices) apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
    description = "Deletes the root build directory and clears previous build outputs."
}

subprojects {
    afterEvaluate {
        if (plugins.hasPlugin("com.android.application") || plugins.hasPlugin("com.android.library")) {
            dependencies {
                // Local Unit Test Sandboxes
                "testImplementation"(libs.junit)
                // Instrumented Device Integration Tests
                "androidTestImplementation"(libs.androidx.junit)
                "androidTestImplementation"(libs.androidx.espresso.core)
            }
        }
    }
}