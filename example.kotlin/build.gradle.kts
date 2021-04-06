import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("kapt")
    `kotlin-generator`
}

dependencies {
    implementation("io.art.java:core")
    implementation("io.art.java:scheduler")
    implementation("io.art.java:configurator")
    implementation("io.art.java:server")
    implementation("io.art.java:communicator")
    implementation("io.art.java:value")
    implementation("io.art.java:logging")
    implementation("io.art.java:launcher")
    implementation("io.art.java:model")
    implementation("io.art.java:xml")
    implementation("io.art.java:rsocket")
    implementation("io.art.java:http")
    implementation("io.art.java:json")
    implementation("io.art.java:protobuf")
    implementation("io.art.java:message-pack")
    implementation("io.art.java:yaml")
    implementation("io.art.java:graal")
    implementation("io.art.java:rocks-db")
    implementation("io.art.kotlin:kotlin-extensions")
    kapt("io.art.kotlin:kotlin-extensions")
    kapt(project(":language.kotlin"))
}

tasks.withType(KotlinCompile::class.java).all {
    kotlinOptions {
        apiVersion = "1.4"
        languageVersion = "1.4"
    }
}
