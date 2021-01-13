plugins {
    kotlin("jvm") version "1.4.20"
}

dependencies {
    implementation(project(":language.java"))
    api("org.jetbrains.kotlin","kotlin-compiler-embeddable", "1.+")
    api("org.jetbrains.kotlin","kotlin-annotation-processing-gradle", "1.+")
}
