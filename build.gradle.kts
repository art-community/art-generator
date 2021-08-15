import io.art.gradle.common.constants.BUILD_EXECUTABLE_JAR_TASK
import io.art.gradle.common.constants.WRITE_CONFIGURATION_TASK
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    id("art-internal-jvm")
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

    embedded("io.art.kotlin:core:main")
    embedded("io.art.kotlin:launcher:main")
    embedded("io.art.kotlin:configurator:main")
    embedded("io.art.kotlin:scheduler:main")
    embedded("io.art.kotlin:logging:main")
    embedded("io.art.kotlin:meta:main")

    embedded("org.projectlombok", "lombok", lombokVersion)
    embedded("com.squareup", "javapoet", javaPoetVersion)
    embedded("com.squareup", "kotlinpoet", kotlinPoetVersion)

    testImplementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("compiler-embeddable"))
    testImplementation(kotlin("reflect"))

    testImplementation("io.art.kotlin:core:main")
    testImplementation("io.art.kotlin:launcher:main")
    testImplementation("io.art.kotlin:configurator:main")
    testImplementation("io.art.kotlin:scheduler:main")
    testImplementation("io.art.kotlin:logging:main")
    testImplementation("io.art.kotlin:meta:main")

    testCompileOnly("org.projectlombok", "lombok", lombokVersion)
    annotationProcessor("org.projectlombok", "lombok", lombokVersion)
    testAnnotationProcessor("org.projectlombok", "lombok", lombokVersion)

    testImplementation("org.junit.jupiter", "junit-jupiter-api", junitVersion)
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", junitVersion)
}

sourceSets {
    test {
        java {
            srcDir("src/test/kotlin-sources")
            srcDir("src/test/java-sources")
            srcDir("src/test/mixed-sources")
        }
    }
}

generator {
    source("Example") {
        jvm()
        sourcesPattern { exclude("**/main/*", "**/kotlin/*") }
    }
    main {
        disableRunning()
    }
}

executable {
    jar {
        configureRun {
            val configurationPath = generator.mainConfiguration.workingDirectory
                    .resolve("module.yml")
                    .toFile()
                    .absolutePath
            dependsOn(WRITE_CONFIGURATION_TASK)
            jvmArgs("-Dconfiguration=$configurationPath")
        }
    }
    main("io.art.generator.Generator")
}

tasks.build {
    dependsOn("build-executable-jar")
}

afterEvaluate {
    tasks.findByName("publish")?.dependsOn("build-executable-jar")
}

tasks.test {
    val configurationPath = generator.mainConfiguration.workingDirectory
            .resolve("module.yml")
            .toFile()
            .absolutePath
    dependsOn(BUILD_EXECUTABLE_JAR_TASK)
    dependsOn(WRITE_CONFIGURATION_TASK)
    useJUnitPlatform()
    jvmArgs("-Xms2g", "-Xmx2g")
    jvmArgs("-Dconfiguration=$configurationPath")
    jvmArgs("-Djar=${buildDir.resolve("executable").resolve("art-generator.jar").absolutePath}")
}
