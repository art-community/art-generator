import org.gradle.internal.jvm.Jvm


val googleAutoVersion: String by project
val googleFormatterVersion: String by project

dependencies {
    if (JavaVersion.current().isJava8) {
        implementation(files(Jvm.current().toolsJar))
    }
    annotationProcessor("com.google.auto.service", "auto-service", googleAutoVersion)
    implementation("com.google.auto.service", "auto-service", googleAutoVersion)
    implementation("com.google.googlejavaformat", "google-java-format", googleFormatterVersion)

    implementation(project(":launcher"))
    implementation(project(":core"))
    implementation(project(":configurator"))
    implementation(project(":server"))
    implementation(project(":communicator"))
    implementation(project(":value"))
    implementation(project(":model"))
    implementation(project(":rsocket"))
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
