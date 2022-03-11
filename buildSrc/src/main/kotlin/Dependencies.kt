object Dependencies {
    /**
     * To define plugins
     */
    object BuildPlugins {
        val androidGradle by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
        val kotlinGradle by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
    }

    /**
     * To define dependencies
     */
    object Deps {
        val androidCoreKtx by lazy {"androidx.core:core-ktx:${Versions.androidxCoreKtx}"}
        val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
        val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }
        val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}" }
        val materialDesign by lazy { "com.google.android.material:material:${Versions.material}" }
        val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
        val junit by lazy { "junit:junit:${Versions.jUnit}" }
        val androidJunit by lazy { "androidx.test.ext:junit:${Versions.androidJunit}" }
        val espresso by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }
    }
}