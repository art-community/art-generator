plugins {
    kotlin("jvm") version "1.4.20"
    kotlin("kapt") version "1.4.30-M1"
}

kapt {
    includeCompileClasspath = false
    correctErrorTypes = true
}

dependencies {
    implementation(project(":language.java"))
    api("com.squareup", "kotlinpoet", "1.7.2")
    api("com.squareup", "kotlinpoet-metadata", "1.7.2")
    api("com.squareup", "kotlinpoet-metadata-specs", "1.7.2")
    api("org.jetbrains.kotlin","kotlin-compiler-embeddable", "1.+")
}
