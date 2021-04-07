import org.gradle.internal.jvm.Jvm


val googleFormatterVersion: String by project
val lombokVersion: String by project

dependencies {
    if (JavaVersion.current().isJava8) {
        api(files(Jvm.current().toolsJar))
    }
    api("com.google.googlejavaformat", "google-java-format", googleFormatterVersion)
    api("io.art.java:launcher")
    api("io.art.java:core")
    api("io.art.java:configurator")
    api("io.art.java:server")
    api("io.art.java:communicator")
    api("io.art.java:value")
    api("io.art.java:model")
    api("io.art.java:rsocket")
    api("io.art.java:http")
    api("io.art.java:json")
    api("io.art.java:message-pack")
    api("io.art.java:protobuf")
    api("io.art.java:scheduler")
    api("io.art.java:logging")
    api("io.art.java:rocks-db")
    compileOnly("org.projectlombok", "lombok", lombokVersion)
    annotationProcessor("org.projectlombok", "lombok", lombokVersion)
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
