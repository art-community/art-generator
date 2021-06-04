import io.art.gradle.common.constants.WRITE_CONFIGURATION_TASK
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    id("art-internal-jvm") version "main"
    kotlin("jvm")
}

tasks.withType(type = Wrapper::class) {
    gradleVersion = "7.0"
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = JavaVersion.current().toString()
    }
}

allprojects {
    group = "io.art.generator"
    repositories { mavenCentral() }
}

dependencies {
    val lombokVersion: String by project
    val junitVersion: String by project
    val javaPoetVersion: String by project
    val kotlinPoetVersion: String by project

    embedded(kotlin("stdlib-jdk8"))
    embedded(kotlin("compiler-embeddable"))
    embedded(kotlin("reflect"))

    embedded("io.art.java:core:main")
    embedded("io.art.java:launcher:main")
    embedded("io.art.java:configurator:main")
    embedded("io.art.java:yaml-configuration:main")
    embedded("io.art.java:value:main")
    embedded("io.art.java:model:main")
    embedded("io.art.java:scheduler:main")
    embedded("io.art.java:logging:main")
    embedded("io.art.java:meta:main")

    testImplementation("io.art.java:core:main")
    testImplementation("io.art.java:launcher:main")
    testImplementation("io.art.java:configurator:main")
    testImplementation("io.art.java:yaml-configuration:main")
    testImplementation("io.art.java:value:main")
    testImplementation("io.art.java:model:main")
    testImplementation("io.art.java:scheduler:main")
    testImplementation("io.art.java:logging:main")
    testImplementation("io.art.java:meta:main")

    embedded("com.squareup", "javapoet", javaPoetVersion)
    embedded("com.squareup", "kotlinpoet", kotlinPoetVersion)

    embedded("org.projectlombok", "lombok", lombokVersion)

    testCompileOnly("org.projectlombok", "lombok", lombokVersion)
    testAnnotationProcessor("org.projectlombok", "lombok", lombokVersion)

    testImplementation("org.junit.jupiter", "junit-jupiter-api", junitVersion)
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", junitVersion)
}

executable {
    jar()
    main("io.art.generator.Generator")
}

val testSourceSet: SourceSet = sourceSets.test.get()
generator {
    java(testSourceSet.java)
    configurationPath(testSourceSet.resources.sourceDirectories.first().resolve("module.yml").toPath())
}

tasks.test {
    dependsOn(WRITE_CONFIGURATION_TASK)
    useJUnitPlatform()
    generator.configurationPath.toFile().delete()
}
