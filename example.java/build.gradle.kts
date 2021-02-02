plugins {
    id("com.palantir.graal") version "0.7.2"
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
    annotationProcessor(project(":language.java"))
}

val compileJava: JavaCompile = tasks["compileJava"] as JavaCompile
val processResources: Task = tasks["processResources"]

compileJava.options.compilerArgs.addAll(arrayOf(
        "-Aart.generator.recompilation.destination=${compileJava.destinationDir.absolutePath}",
        "-Aart.generator.recompilation.classpath=${compileJava.classpath.files.joinToString(",")}",
        "-Aart.generator.recompilation.sources=${compileJava.source.files.joinToString(",")}",
        "-Aart.generator.recompilation.sourcesRoot=${sourceSets.main.get().java.sourceDirectories.asPath}"
))

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
