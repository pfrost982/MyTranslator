import org.jetbrains.kotlin.gradle.targets.js.npm.includedRange

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "geekbrains.ru.translator"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    //AndroidX
    implementation("androidx.appcompat:appcompat:1.0.2")

    //Design
    //You should not use the com.android.support and com.google.android.material dependencies in your app at the same time
    implementation("com.google.android.material:material:1.0.0")

    //Kotlin
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.5.20")

    //Rx-Java
    implementation("io.reactivex.rxjava2:rxandroid:2.1.0")
    implementation("io.reactivex.rxjava2:rxjava:2.2.8'")

    //Retrofit 2
    implementation("com.squareup.retrofit2:retrofit:2.6.0")
    implementation("com.squareup.retrofit2:converter-gson:2.6.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.12.1")
    implementation("com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0")

    //Koin core features
    implementation("io.insert-koin:koin-core:3.1.2")
    //Koin main features for Android (Scope,ViewModel ...)
    implementation( "io.insert-koin:koin-android:3.1.2")
    //Koin Java Compatibility
    implementation( "io.insert-koin:koin-android-compat:3.1.2")
    //Glide
    implementation("com.github.bumptech.glide:glide:4.11.0")
    kapt("com.github.bumptech.glide:compiler:4.11.0")
    //Room
    implementation("androidx.room:room-runtime:2.3.0")
    kapt("androidx.room:room-compiler:2.3.0")
    implementation("androidx.room:room-ktx:2.3.0")

    //data
    implementation(project(":data"))

    implementation("io.reactivex.rxjava2:rxjava:2.2.8")
    //Test
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
    //implementation fileTree (dir: 'libs', include: ['*.jar'])
}

