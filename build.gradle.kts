plugins {
    `java-library`
}

group = "io.art.generator"

allprojects {
    repositories {
        jcenter()
        mavenCentral()
    }
}

subprojects {
    group = rootProject.group
    apply(plugin = "java-library")
}
