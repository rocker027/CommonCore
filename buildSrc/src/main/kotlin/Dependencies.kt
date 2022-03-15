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
        val androidTestCoreKtx by lazy { "androidx.test:core-ktx:${Versions.androidTest}" }
        val androidTestCore by lazy { "androidx.test:core:${Versions.androidTest}" }
        val androidRules by lazy { "androidx.test:rules:${Versions.androidRules}" }
        val androidRunner by lazy { "androidx.test:runner:${Versions.androidRunner}" }
        val androidJunitKtx by lazy { "androidx.test.ext:junit-ktx:${Versions.jUnit}" }
        val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
        val hiltViewModel by lazy { "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt}" }
        val hiltCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }
        val kotlinCoroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}" }
        val kotlinCoroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}" }
        val kotlinCoroutinesTest by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}" }
        val kotlinDateTime by lazy { "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinDateTime}" }
        val androidCoreKtx by lazy { "androidx.core:core-ktx:${Versions.androidxCoreKtx}" }
        val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
        val activityKtx by lazy { "androidx.activity:activity-ktx:${Versions.activityKtx}" }
        val fragmentKtx by lazy { "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}" }
        val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }
        val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}" }
        val materialDesign by lazy { "com.google.android.material:material:${Versions.material}" }
        val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }

        // lifecycle
        val lifecycleKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}" }
        val lifecycleViewModelKtx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        val lifecycleLiveDataKtx =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        val lifecycleSaveState =
            "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
        val lifecycleProcess =
            "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"
        val lifecycleReactLiveStreamsKtx =
            "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.lifecycle}"

        val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
        val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
        val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"

        val truth = "com.google.truth:truth:${Versions.truth}"
        val mockkCore = "io.mockk:mockk:${Versions.mockk}"
        val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"
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

fun Project.importTestDependencies() {
    dependencies {
        val testImplementation by configurations
        val androidTestImplementation by configurations

        /**
         * @link https://developer.android.com/training/testing/instrumented-tests/androidx-test-libraries/test-setup
         */

        // Core library
        androidTestImplementation(Dependencies.Deps.androidTestCore)
        androidTestImplementation(Dependencies.Deps.androidTestCoreKtx)

        // AndroidJUnitRunner and JUnit Rules
        androidTestImplementation(Dependencies.Deps.androidRunner)
        androidTestImplementation(Dependencies.Deps.androidRules)

        // Assertions
        androidTestImplementation(Dependencies.Deps.androidJunit)
        androidTestImplementation(Dependencies.Deps.androidJunitKtx)
        androidTestImplementation(Dependencies.Deps.truth)

        androidTestImplementation(Dependencies.Deps.kotlinCoroutinesTest)

        testImplementation(Dependencies.Deps.junit)
        testImplementation(Dependencies.Deps.mockkCore)
        androidTestImplementation(Dependencies.Deps.mockkAndroid)
        androidTestImplementation(Dependencies.Deps.androidJunit)
        androidTestImplementation(Dependencies.Deps.espresso)
    }
}

fun Project.importCommonDependencies() {
    dependencies {
        val implementation by configurations
        val kapt by configurations

        implementation(Dependencies.Deps.androidCoreKtx)
        implementation(Dependencies.Deps.appCompat)
        implementation(Dependencies.Deps.materialDesign)
        implementation(Dependencies.Deps.constraintLayout)
        implementation(Dependencies.Deps.activityKtx)
        implementation(Dependencies.Deps.fragmentKtx)
        // lifecycle
        implementation(Dependencies.Deps.lifecycleKtx)
        implementation(Dependencies.Deps.lifecycleViewModelKtx)
        implementation(Dependencies.Deps.lifecycleSaveState)
        implementation(Dependencies.Deps.lifecycleLiveDataKtx)
        implementation(Dependencies.Deps.lifecycleProcess)
        implementation(Dependencies.Deps.lifecycleReactLiveStreamsKtx)
    }
}

fun Project.importHiltDependencies() {
    dependencies {
        val implementation by configurations
        val kapt by configurations

        implementation(Dependencies.Deps.hiltAndroid)
        implementation(Dependencies.Deps.hiltViewModel)
        kapt(Dependencies.Deps.hiltCompiler)
    }
}

fun Project.importThirdPartyDependencies() {
    dependencies {
        val implementation by configurations
        val kapt by configurations

        implementation(Dependencies.Deps.timber)
        implementation(Dependencies.Deps.moshiKotlin)
        implementation(Dependencies.Deps.moshiCodegen)
        implementation(Dependencies.Deps.moshiAdapters)
    }
}

fun Project.importKotlinCoroutines() {
    dependencies {
        val implementation by configurations
        val kapt by configurations

        implementation(Dependencies.Deps.kotlinCoroutinesCore)
        implementation(Dependencies.Deps.kotlinCoroutinesAndroid)
    }
}