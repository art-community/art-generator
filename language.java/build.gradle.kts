import org.gradle.internal.jvm.Jvm

val googleFormatterVersion: String by project

dependencies {
    if (JavaVersion.current().isJava8) {
        api(files(Jvm.current().toolsJar))
    }
    api("com.google.googlejavaformat", "google-java-format", googleFormatterVersion)
    api("io.art:launcher")
    api("io.art:core")
    api("io.art:configurator")
    api("io.art:server")
    api("io.art:communicator")
    api("io.art:value")
    api("io.art:model")
    api("io.art:rsocket")
    api("io.art:http")
    api("io.art:json")
    api("io.art:message-pack")
    api("io.art:protobuf")
    api("io.art:scheduler")
    api("io.art:logging")
    api("io.art:rocks-db")
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

