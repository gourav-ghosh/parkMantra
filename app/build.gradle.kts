plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.parkmantra"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.parkmantra"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures{
        dataBinding=true
        buildConfig=true
        viewBinding = true
    }
    flavorDimensions += listOf("default")
    productFlavors {
        create("development") {
            dimension = "default"
            buildConfigField("String", "BASE_URL",  "\"https://9002-idx-parkmantra-1730691768146.cluster-bec2e4635ng44w7ed22sa22hes.cloudworkstations.dev/app/\"")
        }
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-bom:32.7.2")
    implementation ("com.google.firebase:firebase-messaging:23.4.1")
    implementation("com.google.firebase:firebase-crashlytics:18.6.2")
    implementation("com.google.firebase:firebase-analytics:21.5.1")
    implementation("com.android.support:support-annotations:28.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("io.github.chaosleung:pinview:1.4.4")
    implementation ("com.google.code.gson:gson:2.8.8")
    implementation ("commons-lang:commons-lang:2.6")
    implementation ("com.koushikdutta.ion:ion:3.1.0")

    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.github.skydoves:powerspinner:1.2.4")

    implementation ("com.nimbusds:nimbus-jose-jwt:9.8")
//    implementation ("com.github.tobiasschuerg:android-prefix-suffix-edit-text:1.3.1")
//    implementation ("com.github.markusfisch:BarcodeScannerView:1.5.1")
    implementation ("io.socket:socket.io-client:2.1.0")
    implementation ("pub.devrel:easypermissions:3.0.0")
    implementation ("io.github.chochanaresh:filepicker:0.2.3")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
//    implementation ("com.github.douglasjunior:android-simple-tooltip:1.1.0")
    implementation ("com.jsibbold:zoomage:1.3.1")
    implementation ("com.github.wseemann:FFmpegMediaMetadataRetriever:1.0.14")
    implementation ("com.google.android.exoplayer:exoplayer:2.18.1")
    implementation ("com.vanniktech:android-image-cropper:4.5.0")
}