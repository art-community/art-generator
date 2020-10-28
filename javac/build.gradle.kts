import org.gradle.internal.jvm.Jvm

dependencies {
    if (JavaVersion.current().isJava8) {
        implementation(files(Jvm.current().toolsJar))
    }
    annotationProcessor("com.google.auto.service", "auto-service", "1+")
    implementation("com.google.auto.service", "auto-service", "1+")
    implementation(project(":core"))
    implementation(project(":model"))
}


if (!JavaVersion.current().isJava8) {
    tasks.withType<JavaCompile> {
        options.compilerArgs.addAll(arrayOf(
                "--add-exports", "jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED",
                "--add-exports", "jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED"
        ))
    }
}
