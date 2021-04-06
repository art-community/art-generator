plugins {
    idea
    `java-library`
}

group = "io.art"


subprojects {
    group = rootProject.group

    repositories {
        jcenter()
        mavenCentral()
        maven {
            url = uri("https://repo.spring.io/milestone")
        }
    }

    apply(plugin = "java-library")

    dependencies {
        val lombokVersion: String by project
        compileOnly("org.projectlombok", "lombok", lombokVersion)
        annotationProcessor("org.projectlombok", "lombok", lombokVersion)
    }
}
