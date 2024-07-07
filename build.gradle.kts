import io.art.gradle.common.constants.BUILD_JAR_EXECUTABLE_TASK
import io.art.gradle.common.constants.WRITE_CONFIGURATION_TASK

plugins {
    `java-library`
    id("art-internal-jvm")
    kotlin("jvm")
}

tasks.withType(type = Wrapper::class) {
    gradleVersion = "8.5"
}

allprojects {
    group = "io.art.generator"
    repositories { mavenCentral() }
}

dependencies {
    val lombokVersion: String by project
    val junitVersion: String by project
    val javaPoetVersion: String by project
    val kotlinPoetVersion: String by project
    val artKotlinVersion: String by project
    val kotlinCompilerVersion: String by project
    val kotlinCompilingTestingVersion: String by project

    embedded(kotlin("stdlib-jdk8"))
    embedded(kotlin("compiler-embeddable", kotlinCompilerVersion))
    embedded(kotlin("reflect"))

    embedded("io.art.kotlin:core:$artKotlinVersion")
    embedded("io.art.kotlin:launcher:$artKotlinVersion")
    embedded("io.art.kotlin:configurator:$artKotlinVersion")
    embedded("io.art.kotlin:logging:$artKotlinVersion")
    embedded("io.art.kotlin:meta:$artKotlinVersion")

    embedded("org.projectlombok", "lombok", lombokVersion)
    embedded("com.squareup", "javapoet", javaPoetVersion)
    embedded("com.squareup", "kotlinpoet", kotlinPoetVersion)

    testImplementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("compiler-embeddable", kotlinCompilerVersion))
    testImplementation(kotlin("reflect"))

    testImplementation("io.art.kotlin:core:$artKotlinVersion")
    testImplementation("io.art.kotlin:launcher:$artKotlinVersion")
    testImplementation("io.art.kotlin:configurator:$artKotlinVersion")
    testImplementation("io.art.kotlin:scheduler:$artKotlinVersion")
    testImplementation("io.art.kotlin:logging:$artKotlinVersion")
    testImplementation("io.art.kotlin:meta:$artKotlinVersion")
    testImplementation("dev.zacsweers.kctfork:core:$kotlinCompilingTestingVersion")
    testImplementation("org.projectlombok", "lombok", lombokVersion)

    annotationProcessor("org.projectlombok", "lombok", lombokVersion)
    testAnnotationProcessor("org.projectlombok", "lombok", lombokVersion)

    testImplementation("org.junit.jupiter", "junit-jupiter-api", junitVersion)
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", junitVersion)
}

sourceSets {
    test {
        java {
            srcDir("src/test/kotlin-sources")
            srcDir("src/test/java-sources")
            srcDir("src/test/mixed-sources")
        }
    }
}

generator {
    source("Example") {
        modulePackage("")
        jvm()
        sourcesPattern { exclude("**/main/*", "**/kotlin/*") }
    }
    main {
        consoleLogging()
        disableRunning()
    }
}

executable {
    jar {
        configureRun {
            val configurationPath = generator.mainConfiguration.workingDirectory
                .resolve("module.yml")
                .toFile()
                .absolutePath
            dependsOn(WRITE_CONFIGURATION_TASK)
            jvmArgs("-Xms2g", "-Xmx2g")
            jvmArgs("--add-exports", "jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED")
            jvmArgs("--add-exports", "jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED")
            jvmArgs("--add-exports", "jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED")
            jvmArgs("--add-exports", "jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED")
            jvmArgs("--add-exports", "jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED")
            jvmArgs("--add-exports", "jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED")
            jvmArgs("--add-exports", "jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED")
            jvmArgs("--add-exports", "jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED")
            jvmArgs("-Dconfiguration=$configurationPath")
        }
    }
    main("io.art.generator.Generator")
}

tasks.build {
    dependsOn(BUILD_JAR_EXECUTABLE_TASK)
}

afterEvaluate {
    tasks.forEach { task -> if (task.name.contains("publish")) task.dependsOn("build-jar-executable") }
}

tasks.test {
    testLogging {
        showStandardStreams = true
    }
    val configurationPath = generator.mainConfiguration.workingDirectory
        .resolve("module.yml")
        .toFile()
        .absolutePath
    dependsOn(BUILD_JAR_EXECUTABLE_TASK)
    dependsOn(WRITE_CONFIGURATION_TASK)
    useJUnitPlatform()
    jvmArgs("-Xms2g", "-Xmx2g")
    jvmArgs("--add-exports", "jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED")
    jvmArgs("--add-exports", "jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED")
    jvmArgs("--add-exports", "jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED")
    jvmArgs("--add-exports", "jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED")
    jvmArgs("--add-exports", "jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED")
    jvmArgs("--add-exports", "jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED")
    jvmArgs("--add-exports", "jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED")
    jvmArgs("--add-exports", "jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED")
    jvmArgs("-Dconfiguration=$configurationPath")
    jvmArgs("-Djar=${layout.buildDirectory.file("executable").get().asFile.resolve("art-generator.jar").absolutePath}")
}
