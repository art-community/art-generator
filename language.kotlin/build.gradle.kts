plugins {
    kotlin("jvm") version "1.4.20"
}

dependencies {
    implementation(project(":language.java"))
    implementation("org.jetbrains.kotlin", "kotlin-compiler-embeddable", "1.+")
    implementation("org.jetbrains.kotlin", "kotlin-annotation-processing-gradle", "1.+")
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
