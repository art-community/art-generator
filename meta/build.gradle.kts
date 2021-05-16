import org.gradle.api.file.DuplicatesStrategy.INCLUDE
import org.gradle.internal.jvm.Jvm
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

val included: Configuration by configurations.creating { configurations.implementation.get().extendsFrom(this) }

val kotlinVersion: String by project

dependencies {
    if (JavaVersion.current().isJava8) {
        included(files(Jvm.current().toolsJar))
    }

    included(kotlin("stdlib-jdk8"))
    included(kotlin("compiler-embeddable"))
    included(kotlin("reflect"))

    included("io.art.java:core:main")
    included("io.art.java:launcher:main")
    included("io.art.java:configurator:main")
    included("io.art.java:yaml-configuration:main")
    included("io.art.java:server:main")
    included("io.art.java:communicator:main")
    included("io.art.java:value:main")
    included("io.art.java:model:main")
    included("io.art.java:rsocket:main")
    included("io.art.java:http:main")
    included("io.art.java:json:main")
    included("io.art.java:yaml:main")
    included("io.art.java:xml:main")
    included("io.art.java:message-pack:main")
    included("io.art.java:protobuf:main")
    included("io.art.java:scheduler:main")
    included("io.art.java:logging:main")
    included("io.art.java:rocks-db:main")
    included("io.art.java:storage:main")
    included("io.art.java:tarantool:main")
    included("io.art.java:meta:main")

    included("com.squareup", "javapoet", "+")
    included("com.squareup", "kotlinpoet", "+")
    included("net.sourceforge.argparse4j", "argparse4j", "+")
    included("org.projectlombok", "lombok", "+")
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
    from(included.filter { it.extension != "gz" }.map { if (it.isDirectory) it else zipTree(it) })
}

tasks.register("print-classpath") {
    doLast {
        included.files.forEach {
            println("- $it")
        }
    }
}
