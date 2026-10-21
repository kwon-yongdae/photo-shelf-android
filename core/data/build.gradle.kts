import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id("kotlinx-serialization")
}

val localProperties = Properties().apply {
    rootProject.file("local.properties").inputStream().use { load(it) }
}

val unsplashAccessKey = requireNotNull(
    localProperties.getProperty("UNSPLASH_ACCESS_KEY")
) {
    "local.properties에 UNSPLASH_ACCESS_KEY를 설정해 주세요."
}

val unsplashBaseUrl = requireNotNull(
    localProperties.getProperty("UNSPLASH_BASE_URL")
) {
    "local.properties에 UNSPLASH_BASE_URL을 설정해 주세요."
}

android {
    buildFeatures {
        buildConfig = true
    }

    namespace = "com.yongdae.photoshelf.core.data"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "UNSPLASH_BASE_URL",
            "\"${unsplashBaseUrl.replace("\\", "\\\\").replace("\"", "\\\"")}\"",
        )
        buildConfigField(
            "String",
            "UNSPLASH_ACCESS_KEY",
            "\"${unsplashAccessKey.replace("\\", "\\\\").replace("\"", "\\\"")}\"",
        )
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}