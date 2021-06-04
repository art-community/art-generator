import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    id("art-internal-jvm") version "main"
    kotlin("jvm")
}


tasks.withType(type = Wrapper::class) {
    gradleVersion = "7.0"
}

allprojects {
    group = "io.art.generator"
    repositories { mavenCentral() }
}

val included: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
}

dependencies {
    included(kotlin("stdlib-jdk8"))
    included(kotlin("compiler-embeddable"))
    included(kotlin("reflect"))

    included("io.art.java:core:main")
    included("io.art.java:launcher:main")
    included("io.art.java:configurator:main")
    included("io.art.java:yaml-configuration:main")
    included("io.art.java:value:main")
    included("io.art.java:model:main")
    included("io.art.java:scheduler:main")
    included("io.art.java:logging:main")
    included("io.art.java:meta:main")
    included("com.squareup", "javapoet", "+")
    included("com.squareup", "kotlinpoet", "+")
    included("org.projectlombok", "lombok", "+")

    testCompileOnly("org.projectlombok", "lombok", "+")
    testAnnotationProcessor("org.projectlombok", "lombok", "+")

    testImplementation("org.junit.jupiter:junit-jupiter-api:+")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:+")
    testRuntime("org.junit.platform:junit-platform-console:+")
}


val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = JavaVersion.current().toString()
    }
}

tasks.register("build-executable-jar", org.gradle.jvm.tasks.Jar::class.java) {
    val compileKotlin: KotlinCompile = tasks["compileKotlin"] as KotlinCompile
    val processResources: Task = tasks["processResources"]

    group = "art"
    dependsOn("build")

    manifest {
        attributes(mapOf("Main-Class" to "io.art.generator.meta.MetaGenerator", "Multi-Release" to "true"))
    }

    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    from(processResources.outputs.files)
    from(compileKotlin.outputs.files)
    from(included.mapNotNull { dependency -> if (dependency.isDirectory) dependency else zipTree(dependency) })
}


val testSourceSet: SourceSet = sourceSets.test.get()
val configurationFile = testSourceSet
        .resources
        .sourceDirectories
        .first()
        .apply { mkdirs() }
        .resolve("module.yml")

task("prepare") {
    group = "art"
    doFirst {
        val configuration = """
            logging:
              default:
                writers:
                  - type: console
                    colored: false
            module:
              name: Example
            paths:
              sources: ${testSourceSet.java.sourceDirectories.first()}
            watcher:
              period: 300ms
            analyzer:
              delay: 1s
            classpath: ${included.files.joinToString(if (OperatingSystem.current().isWindows) ";" else ":")}
        """.trimIndent()
        configurationFile.writeText(configuration)
    }
}

tasks.test {
    dependsOn("prepare")
    useJUnitPlatform()
    doLast { configurationFile.delete() }
}
