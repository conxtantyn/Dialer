object Dependencies {
    object Version {
        const val Gson = "2.8.6"
        const val Timber = "4.7.1"

        const val GooglePlayCore = "1.10.3"
        const val GooglePlayCoreKtx = "1.8.1"

        const val AndroidxCore = "1.7.0"
        const val AndroidxAppCompact = "1.4.2"
        const val AndroidxConstraintLayout = "2.1.4"
        const val AndroidGoogleMaterial = "1.6.1"
        const val AndroidxLegacySupport = "1.0.0"

        const val Navigation = "2.4.1"

        const val Dagger = "2.42"
        const val DaggerAssisted = "0.5.2"

        const val Lottie = "4.2.0"

        const val Junit = "4.13.2"
        const val AndroidxTest = "1.1.3"
        const val AndroidxEspresso = "3.4.0"
    }
    const val Gson = "com.google.code.gson:gson:${Version.Gson}"
    const val Timber = "com.jakewharton.timber:timber:${Version.Timber}"

    // google
    const val GooglePlayCore = "com.google.android.play:core:${Version.GooglePlayCore}"
    const val GooglePlayCoreKtx = "com.google.android.play:core-ktx:${Version.GooglePlayCoreKtx}"

    // android
    const val AndroidxCore = "androidx.core:core-ktx:${Version.AndroidxCore}"
    const val AndroidxAppCompact = "androidx.appcompat:appcompat:${Version.AndroidxAppCompact}"
    const val AndroidxConstraintLayout = "androidx.constraintlayout" +
            ":constraintlayout:${Version.AndroidxConstraintLayout}"
    const val AndroidGoogleMaterial = "com.google.android.material" +
            ":material:${Version.AndroidGoogleMaterial}"
    const val AndroidxLegacySupport = "androidx.legacy:legacy-support-v4" +
            ":${Version.AndroidxLegacySupport}"

    // navigation
    const val NavigationUI = "androidx.navigation:navigation-ui-ktx:${Version.Navigation}"
    const val NavigationFragment = "androidx.navigation:navigation-fragment-ktx" +
            ":${Version.Navigation}"

    // dagger
    const val Dagger = "com.google.dagger:dagger:${Version.Dagger}"
    const val DaggerAndroid = "com.google.dagger:dagger-android-support:${Version.Dagger}"
    const val DaggerCompiler = "com.google.dagger:dagger-compiler:${Version.Dagger}"
    const val DaggerAndroidProcessor = "com.google.dagger" +
            ":dagger-android-processor:${Version.Dagger}"
    const val DaggerAssistedAnnotations = "com.squareup.inject" +
            ":assisted-inject-annotations-dagger2:${Version.DaggerAssisted}"
    const val DaggerAssistedProcessor = "com.squareup.inject" +
            ":assisted-inject-processor-dagger2:${Version.DaggerAssisted}"

    // lottie
    const val Lottie = "com.airbnb.android:lottie:${Version.Lottie}"

    // test
    const val Junit = "junit:junit:${Version.Junit}"
    const val AndroidxTest = "androidx.test.ext:junit:${Version.AndroidxTest}"
    const val AndroidxEspresso = "androidx.test.espresso:espresso-core:${Version.AndroidxEspresso}"
}