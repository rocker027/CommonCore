plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = ConfigExtensions.compileSdkVersion

    defaultConfig {
        applicationId = "com.coors.demoproject"
        minSdk = ConfigExtensions.minSdkVersion
        targetSdk = ConfigExtensions.targetSdkVersion
        versionCode = ConfigExtensions.versionCode
        versionName = ConfigExtensions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(Dependencies.Deps.androidCoreKtx)
    implementation(Dependencies.Deps.appCompat)
    implementation(Dependencies.Deps.materialDesign)
    implementation(Dependencies.Deps.constraintLayout)
    testImplementation(Dependencies.Deps.junit)
    androidTestImplementation(Dependencies.Deps.androidJunit)
    androidTestImplementation(Dependencies.Deps.espresso)
}