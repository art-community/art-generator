import org.gradle.internal.jvm.Jvm

val googleFormatterVersion: String by project

dependencies {
    if (JavaVersion.current().isJava8) {
        api(files(Jvm.current().toolsJar))
    }
    api("com.google.googlejavaformat", "google-java-format", googleFormatterVersion)
    api(project(":launcher"))
    api(project(":core"))
    api(project(":configurator"))
    api(project(":server"))
    api(project(":communicator"))
    api(project(":value"))
    api(project(":model"))
    api(project(":rsocket"))
    api(project(":json"))
    api(project(":message-pack"))
    api(project(":protobuf"))
    api(project(":scheduler"))
    api(project(":logging"))
    api(project(":rocks-db"))
}

tasks.withType<JavaCompile> {
    if (!JavaVersion.current().isJava8) {
        options.compilerArgs.addAll(arrayOf(
                "--add-exports", "jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED"
        ))
    }
}

