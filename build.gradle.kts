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
        apiVersion = "1.5"
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
    embedded("io.art.java:scheduler:main")
    embedded("io.art.java:logging:main")
    embedded("io.art.java:meta:main")
    embedded("com.squareup", "javapoet", javaPoetVersion)
    embedded("com.squareup", "kotlinpoet", kotlinPoetVersion)
    embedded("org.projectlombok", "lombok", lombokVersion)

    testImplementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("compiler-embeddable"))
    testImplementation(kotlin("reflect"))
    testImplementation("io.art.java:core:main")
    testImplementation("io.art.java:launcher:main")
    testImplementation("io.art.java:configurator:main")
    testImplementation("io.art.java:yaml-configuration:main")
    testImplementation("io.art.java:value:main")
    testImplementation("io.art.java:scheduler:main")
    testImplementation("io.art.java:logging:main")
    testImplementation("io.art.java:meta:main")

    annotationProcessor("org.projectlombok", "lombok", lombokVersion)

    testCompileOnly("org.projectlombok", "lombok", lombokVersion)
    testAnnotationProcessor("org.projectlombok", "lombok", lombokVersion)
    testImplementation("org.junit.jupiter", "junit-jupiter-api", junitVersion)
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", junitVersion)
}

sourceSets {
    test {
        java.srcDirs("src/test/kotlin-model")
    }
}

val testSourceSet: SourceSet = sourceSets.test.get()
generator {
    java(testSourceSet.java)
    consoleLogging()
}

executable {
    jar {
        configureRun {
            dependsOn(WRITE_CONFIGURATION_TASK)
            jvmArgs("-Dconfiguration=${generator.configurationPath.toFile().absolutePath}")
        }
    }
    main("io.art.generator.Generator")
}

tasks.test {
    dependsOn(WRITE_CONFIGURATION_TASK)
    useJUnitPlatform()
    jvmArgs("-Dconfiguration=${generator.configurationPath.toFile().absolutePath}")
}
