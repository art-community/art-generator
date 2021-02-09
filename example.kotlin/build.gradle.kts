import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.palantir.graal") version "0.7.2"
    kotlin("jvm") version "1.4.20"
    kotlin("kapt") version "1.4.20"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":scheduler"))
    implementation(project(":configurator"))
    implementation(project(":server"))
    implementation(project(":communicator"))
    implementation(project(":value"))
    implementation(project(":logging"))
    implementation(project(":launcher"))
    implementation(project(":model"))
    implementation(project(":xml"))
    implementation(project(":rsocket"))
    implementation(project(":json"))
    implementation(project(":protobuf"))
    implementation(project(":message-pack"))
    implementation(project(":yaml"))
    implementation(project(":graal"))
    implementation(project(":language.kotlin"))
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
            arg("art.generator.recompilation.classpath", configurations.compileClasspath.get().files
                    .toSet()
                    .joinToString(","))
            arg("art.generator.recompilation.sources", compileKotlin
                    .source
                    .files
                    .filter { it.exists() }
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
    option("--no-fallback")
    option("--report-unsupported-elements-at-runtime")
    option("--allow-incomplete-classpath")
    option("--enable-all-security-services")
    option("--initialize-at-build-time=org.apache.logging.log4j")
    option("--initialize-at-run-time=reactor.netty,io.netty,io.rsocket,org.apache.logging.log4j.core.pattern.JAnsiTextRenderer")
    option("-Dio.netty.noUnsafe=true")
}

