plugins {
    id("com.palantir.graal") version "0.7.2"
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
    implementation("io.art:json")
    implementation("io.art:protobuf")
    implementation("io.art:message-pack")
    implementation("io.art:yaml")
    implementation("io.art:graal")
    annotationProcessor(project(":language.java"))
}

val compileJava: JavaCompile = tasks["compileJava"] as JavaCompile
val processResources: Task = tasks["processResources"]

compileJava.doFirst {
    compileJava.options.compilerArgs.addAll(arrayOf(
            "-Aart.generator.recompilation.destination=${compileJava.destinationDir.absolutePath}",
            "-Aart.generator.recompilation.classpath=${compileJava.classpath.files.joinToString(",")}",
            "-Aart.generator.recompilation.sources=${compileJava.source.files.joinToString(",")}"
    ))
}
compileJava.dependsOn("clean").dependsOn(project(":language.java").tasks["build"])

tasks.register("executableJar", Jar::class) {
    group = "build"
    dependsOn(":build")

    manifest {
        attributes(mapOf("Main-Class" to "ru.Example"))
    }

    from(processResources.outputs.files)
    from(compileJava.outputs.files)
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
    windowsVsVarsPath("C:\\Program Files\\Microsoft SDKs\\Windows\\v7.1\\Bin\\SetEnv.cmd")
}
