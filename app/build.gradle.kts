plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt") // Adicionado diretamente sem alias
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.fakeimdb"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.fakeimdb"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Configuração da chave da API do TMDb
        buildConfigField("String", "TMDB_API_KEY", "\"${project.findProperty("TMDB_API_KEY") ?: ""}\"")

        buildFeatures {
            buildConfig = true  // Habilita os campos personalizados do BuildConfig
        }
    }


    buildFeatures {
        viewBinding = true // Ativar ViewBinding
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

    kotlinOptions {
        jvmTarget = "1.8" // Compatibilidade com versões do Kotlin e Java
    }

    // Configuração do Hilt para Injeção de Dependência
    hilt {
        enableAggregatingTask = true
    }
}

dependencies {
    // Dependências essenciais do Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.recyclerview)

    // Retrofit para consumo de API
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // Room para persistência local
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    // ViewModel e LiveData para gerenciamento de ciclo de vida
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)

    // Hilt para Injeção de Dependências
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Glide para carregamento de imagens
    implementation(libs.glide)
    kapt(libs.glide.compiler)

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.1")

    // Dependência para ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0")

    // Dependência para LiveData (caso você utilize)
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.0")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

    // Testes
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
