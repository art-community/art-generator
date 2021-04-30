import org.gradle.internal.jvm.Jvm.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

val googleFormatterVersion: String by project
val lombokVersion: String by project

dependencies {
    if (JavaVersion.current().isJava8) {
        api(files(current().toolsJar))
    }

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("compiler-embeddable"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4+")
    implementation("io.art.java:launcher:main")
    implementation("io.art.java:core:main")
    implementation("io.art.java:configurator:main")
    implementation("io.art.java:yaml-configuration:main")
    implementation("io.art.java:server:main")
    implementation("io.art.java:communicator:main")
    implementation("io.art.java:value:main")
    implementation("io.art.java:model:main")
    implementation("io.art.java:rsocket:main")
    implementation("io.art.java:http:main")
    implementation("io.art.java:json:main")
    implementation("io.art.java:yaml:main")
    implementation("io.art.java:message-pack:main")
    implementation("io.art.java:protobuf:main")
    implementation("io.art.java:scheduler:main")
    implementation("io.art.java:logging:main")
    implementation("io.art.java:rocks-db:main")
    implementation("io.art.java:storage:main")
    implementation("io.art.java:tarantool:main")
    implementation("org.projectlombok", "lombok", lombokVersion)

    api("com.google.googlejavaformat", "google-java-format", googleFormatterVersion)
    api("com.squareup", "javapoet", "+")
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
