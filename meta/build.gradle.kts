import org.gradle.api.file.DuplicatesStrategy.INCLUDE
import org.gradle.internal.jvm.Jvm
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

val embedded: Configuration by configurations.creating { configurations.compileClasspath.get().extendsFrom(this) }

val kotlinVersion: String by project

dependencies {
    if (JavaVersion.current().isJava8) {
        embedded(files(Jvm.current().toolsJar))
    }

    embedded(kotlin("stdlib-jdk8"))
    embedded(kotlin("compiler-embeddable"))
    embedded(kotlin("reflect"))

    embedded("io.art.java:launcher:main")
    embedded("io.art.java:core:main")
    embedded("io.art.java:configurator:main")
    embedded("io.art.java:yaml-configuration:main")
    embedded("io.art.java:server:main")
    embedded("io.art.java:communicator:main")
    embedded("io.art.java:value:main")
    embedded("io.art.java:model:main")
    embedded("io.art.java:rsocket:main")
    embedded("io.art.java:http:main")
    embedded("io.art.java:json:main")
    embedded("io.art.java:yaml:main")
    embedded("io.art.java:message-pack:main")
    embedded("io.art.java:protobuf:main")
    embedded("io.art.java:scheduler:main")
    embedded("io.art.java:logging:main")
    embedded("io.art.java:rocks-db:main")
    embedded("io.art.java:storage:main")
    embedded("io.art.java:tarantool:main")
    embedded("io.art.java:meta:main")

    embedded("com.squareup", "javapoet", "+")
    embedded("com.squareup", "kotlinpoet", "+")
    embedded("net.sourceforge.argparse4j", "argparse4j", "+")
    embedded("org.projectlombok", "lombok", "+")
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = JavaVersion.current().toString()
    }
}

tasks.register("executable-jar", Jar::class.java) {
    val compileKotlin: KotlinCompile = tasks["compileKotlin"] as KotlinCompile
    val processResources: Task = tasks["processResources"]

    group = "build"
    dependsOn("build")

    manifest {
        attributes(mapOf(
                "Main-Class" to "io.art.generator.meta.MetaGenerator",
                "Multi-Release" to "true"
        ))
    }

    duplicatesStrategy = INCLUDE

    from(processResources.outputs.files)
    from(compileKotlin.outputs.files)
    from(embedded.filter { it.extension != "gz" }.map { if (it.isDirectory) it else zipTree(it) })
}

tasks.register("print-classpath") {
    doLast {
        embedded.files.forEach {
            println("- $it")
        }
    }
}
