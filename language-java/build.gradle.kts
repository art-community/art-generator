import org.gradle.internal.jvm.Jvm


val googleFormatterVersion: String by project
val lombokVersion: String by project

dependencies {
    if (JavaVersion.current().isJava8) {
        api(files(Jvm.current().toolsJar))
    }
    api("com.google.googlejavaformat", "google-java-format", googleFormatterVersion)
    api("io.art.java:launcher:main")
    api("io.art.java:core:main")
    api("io.art.java:configurator:main")
    api("io.art.java:server:main")
    api("io.art.java:communicator:main")
    api("io.art.java:value:main")
    api("io.art.java:model:main")
    api("io.art.java:rsocket:main")
    api("io.art.java:http:main")
    api("io.art.java:json:main")
    api("io.art.java:message-pack:main")
    api("io.art.java:protobuf:main")
    api("io.art.java:scheduler:main")
    api("io.art.java:logging:main")
    api("io.art.java:rocks-db:main")
    api("io.art.java:storage:main")
    api("io.art.java:tarantool:main")
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
                "--add-exports", "jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED"
        ))
    }
}
