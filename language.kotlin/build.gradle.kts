plugins {
    kotlin("jvm") version "1.4.20"
}

dependencies {
    implementation(project(":language.java"))
    implementation("org.jetbrains.kotlin","kotlin-compiler-embeddable", "1.+")
    implementation("org.jetbrains.kotlin","kotlin-annotation-processing-gradle", "1.+")
}
