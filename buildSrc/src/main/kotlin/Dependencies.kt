import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate

object Dependencies {
    /**
     * To define plugins
     */
    object BuildPlugins {
        val hiltGradle by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}" }
        val androidGradle by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
        val kotlinGradle by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
    }

    /**
     * To define dependencies
     */
    object Deps {
        val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
        val hiltCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }
        val androidCoreKtx by lazy { "androidx.core:core-ktx:${Versions.androidxCoreKtx}" }
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

val commonPlugins = arrayOf(
    "kotlin-android",
    "kotlin-android-extensions",
    "kotlin-kapt",
    "dagger.hilt.android.plugin"
)


fun Project.importCommonDependencies() {
    dependencies {
        val implementation by configurations
        val kapt by configurations
        val testImplementation by configurations
        val androidTestImplementation by configurations

        implementation(Dependencies.Deps.androidCoreKtx)
        implementation(Dependencies.Deps.appCompat)
        implementation(Dependencies.Deps.materialDesign)
        implementation(Dependencies.Deps.constraintLayout)
        testImplementation(Dependencies.Deps.junit)
        androidTestImplementation(Dependencies.Deps.androidJunit)
        androidTestImplementation(Dependencies.Deps.espresso)
    }
}

fun Project.importHiltDependencies() {
    dependencies {
        val implementation by configurations
        val kapt by configurations
        val testImplementation by configurations
        val androidTestImplementation by configurations

        implementation(Dependencies.Deps.hiltAndroid)
        kapt(Dependencies.Deps.hiltCompiler)
    }
}