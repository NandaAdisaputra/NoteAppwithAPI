plugins {
    alias(libs.plugins.android.application) // Menggunakan alias dari Version Catalog untuk plugin Android.
    alias(libs.plugins.kotlin.android) // Menggunakan alias dari Version Catalog untuk plugin Kotlin Android.
}

android {
    namespace = "com.nandaadisaputra.noteapp" // Menentukan namespace unik untuk aplikasi.
    compileSdk = 35 // Menggunakan API Level 35 (Android 15 Preview), bisa diturunkan ke 34 jika ada masalah.

    defaultConfig {
        applicationId = "com.nandaadisaputra.noteapp" // ID unik untuk aplikasi Android.
        minSdk = 24 // Minimum versi Android yang didukung (Android 7.0 Nougat).
        targetSdk = 35 // Target API Level yang digunakan, sesuaikan dengan compileSdk.
        versionCode = 1 // Versi kode aplikasi untuk Play Store.
        versionName = "1.0" // Versi tampilan untuk pengguna.
        // Menyimpan URL API di dalam BuildConfig agar lebih mudah diakses dan tidak hardcoded dalam kode.
        buildConfigField("String", "BASE_URL", "\"https://script.google.com/macros/s/AKfycbxbC0CrCD_w8wvl5XAXUKW2gObUdb5AzYDEZ1n7j01xJSCI-Fl2SpqfTEh2X0qJ3KAcAA/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        buildConfig = true // Mengaktifkan BuildConfig agar bisa digunakan untuk menyimpan konstanta seperti BASE_URL.
    }

    buildFeatures{
        viewBinding = true // Mengaktifkan View Binding untuk menggantikan findViewById() dengan cara yang lebih aman dan efisien.
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Menyesuaikan kompatibilitas dengan Java 8.
        targetCompatibility = JavaVersion.VERSION_1_8 // Menentukan target Java 8 untuk kompilasi.
    }
    kotlinOptions {
        jvmTarget = "1.8" // Menyesuaikan JVM target agar kompatibel dengan Java 8.
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Lifecycle dan LiveData untuk pengelolaan state yang lebih efisien.
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    // Retrofit untuk melakukan networking (API requests).
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson) // Converter Gson untuk parsing JSON ke objek Kotlin.
    // Kotlin Coroutines untuk menjalankan tugas asinkron secara efisien.
    implementation(libs.kotlinx.coroutines.android)
    // OkHttp Logging Interceptor untuk melihat log request & response dari API.
    implementation(libs.okhttp.logging.interceptor)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

