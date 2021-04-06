plugins {
    kotlin("jvm")
}

val kotlinVersion: String by project

dependencies {
    implementation(project(":language.java"))
    implementation(kotlin("stdlib-jdk8", kotlinVersion))
    implementation("org.jetbrains.kotlin", "kotlin-compiler-embeddable", kotlinVersion)
}
