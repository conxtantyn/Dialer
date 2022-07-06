object Dependencies {
    object Version {
        const val Gson = "2.8.6"
        const val Timber = "4.7.1"

        const val GooglePlayCore = "1.10.3"
        const val GooglePlayCoreKtx = "1.8.1"

        const val Dagger = "2.42"
        const val DaggerAssisted = "0.5.2"
    }
    const val Gson = "com.google.code.gson:gson:${Version.Gson}"
    const val Timber = "com.jakewharton.timber:timber:${Version.Timber}"

    const val GooglePlayCore = "com.google.android.play:core:${Version.GooglePlayCore}"
    const val GooglePlayCoreKtx = "com.google.android.play:core-ktx:${Version.GooglePlayCoreKtx}"

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
}