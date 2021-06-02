import org.gradle.internal.jvm.Jvm

val googleFormatterVersion: String by project
val lombokVersion: String by project

dependencies {
    if (JavaVersion.current().isJava8) {
        api(files(Jvm.current().toolsJar))
    }

    implementation("io.art.java:launcher:main")
    implementation("io.art.java:core:main")
    implementation("io.art.java:configurator:main")
    implementation("io.art.java:server:main")
    implementation("io.art.java:communicator:main")
    implementation("io.art.java:value:main")
    implementation("io.art.java:model:main")
    implementation("io.art.java:rsocket:main")
    implementation("io.art.java:http:main")
    implementation("io.art.java:json:main")
    implementation("io.art.java:message-pack:main")
    implementation("io.art.java:protobuf:main")
    implementation("io.art.java:scheduler:main")
    implementation("io.art.java:logging:main")
    implementation("io.art.java:rocks-db:main")
    implementation("io.art.java:storage:main")
    implementation("io.art.java:tarantool:main")

    compileOnly("org.projectlombok", "lombok", lombokVersion)
    annotationProcessor("org.projectlombok", "lombok", lombokVersion)

    api("com.google.googlejavaformat", "google-java-format", googleFormatterVersion)
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
