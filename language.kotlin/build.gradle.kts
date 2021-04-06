plugins {
    kotlin("jvm") version "1.4.32"
}

val kotlinVersion: String by project

dependencies {
    implementation(project(":language.java"))
    implementation("org.jetbrains.kotlin", "kotlin-compiler-embeddable", kotlinVersion)
}
