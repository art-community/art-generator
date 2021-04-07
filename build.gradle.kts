plugins {
    `java-library`
}

group = "io.art.generator"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = rootProject.group
    apply(plugin = "java-library")
}
