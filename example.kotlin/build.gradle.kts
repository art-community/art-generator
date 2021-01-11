import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.palantir.graal") version "0.7.2"
    kotlin("jvm") version "1.4.20"
    kotlin("kapt") version "1.4.30-M1"
}

dependencies {
    implementation(project(":generator.kotlin"))
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
    kapt(project(":generator.kotlin"))
}

val processResources: Task = tasks["processResources"]
val compileKotlin: KotlinCompile = tasks["compileKotlin"] as KotlinCompile

kapt {
    includeCompileClasspath = false
    javacOptions {
        arguments {
            arg("art.generator.destination", compileKotlin.destinationDir.absolutePath)
            arg("art.generator.classpath", compileKotlin.classpath.asPath)
            arg("art.generator.sources", compileKotlin.source.asPath)
        }
    }
}


compileKotlin.dependsOn("clean").dependsOn(project(":generator.kotlin").tasks["build"])

tasks.register("executableJar", Jar::class) {
    group = "build"
    dependsOn(":build")

    manifest {
        attributes(mapOf("Main-Class" to "ru.Example"))
    }

    from(processResources.outputs.files)
    from(compileKotlin.outputs.files)
    from(configurations.compileClasspath.get().filter { it.extension != "gz" }.map { if (it.isDirectory) it else zipTree(it) })
    from(configurations.runtimeClasspath.get().filter { it.extension != "gz" }.map { if (it.isDirectory) it else zipTree(it) })
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
