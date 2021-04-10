plugins {
    `java-library`
}

group = "io.art.generator"

tasks.withType(type = Wrapper::class) {
    gradleVersion = "7.0-rc-2"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = rootProject.group
    apply(plugin = "java-library")
}
