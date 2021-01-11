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
    api("org.jetbrains.kotlin","kotlin-compiler-embeddable", "1.+")
}
