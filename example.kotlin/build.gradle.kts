import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.palantir.graal") version "0.7.2"
    kotlin("jvm") version "1.4.32"
    kotlin("kapt") version "1.4.32"
}

dependencies {
    implementation("io.art:core")
    implementation("io.art:scheduler")
    implementation("io.art:configurator")
    implementation("io.art:server")
    implementation("io.art:communicator")
    implementation("io.art:value")
    implementation("io.art:logging")
    implementation("io.art:launcher")
    implementation("io.art:model")
    implementation("io.art:xml")
    implementation("io.art:rsocket")
    implementation("io.art:http")
    implementation("io.art:json")
    implementation("io.art:protobuf")
    implementation("io.art:message-pack")
    implementation("io.art:yaml")
    implementation("io.art:graal")
    implementation("io.art:rocks-db")
    implementation("io.art:kotlin-extensions")
    kapt("io.art:kotlin-extensions")
    kapt(project(":language.kotlin"))
}

val processResources: Task = tasks["processResources"]
val compileKotlin: KotlinCompile = tasks["compileKotlin"] as KotlinCompile
val compileJava: JavaCompile = tasks["compileJava"] as JavaCompile

kapt {
    includeCompileClasspath = false
    useBuildCache = false
    javacOptions {
        arguments {
            arg("art.generator.recompilation.destination", compileKotlin
                    .destinationDir
                    .absolutePath)
            arg("art.generator.recompilation.classpath", configurations.compileClasspath
                    .get()
                    .files
                    .toSet()
                    .joinToString(","))
            arg("art.generator.recompilation.sources", compileKotlin
                    .source
                    .files
                    .joinToString(","))
        }
    }
}

tasks.register("executableJar", Jar::class) {
    group = "build"
    dependsOn(":build")

    manifest {
        attributes(mapOf("Main-Class" to "ru.Example"))
    }

    from(processResources.outputs.files)
    from(compileJava.outputs.files)
    from(compileKotlin.outputs.files)
    from(configurations.compileClasspath.get().filter { it.exists() && it.extension != "gz" }.map { if (it.isDirectory) it else zipTree(it) })
    from(configurations.runtimeClasspath.get().filter { it.exists() && it.extension != "gz" }.map { if (it.isDirectory) it else zipTree(it) })
}

graal {
    mainClass("ru.Example")
    outputName("example")
    option("-H:+TraceClassInitialization")
    option("-H:+ReportExceptionStackTraces")
    option("-H:+JNI")
    option("--no-fallback")
    option("--report-unsupported-elements-at-runtime")
    option("--allow-incomplete-classpath")
    option("--enable-all-security-services")
    option("--initialize-at-build-time=org.apache.logging.log4j")
    option("--initialize-at-run-time=reactor.netty,io.netty,io.rsocket,org.apache.logging.log4j.core.pattern.JAnsiTextRenderer")
    option("-Dio.netty.noUnsafe=true")
}

