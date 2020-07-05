plugins {
    id("io.github.art.project") version "1.0.113"
}

art {
    idea()
    embeddedModules {
        kit()
    }
}

dependencies {
    implementation(project(":javac"))
    annotationProcessor(project(":javac"))
}

tasks["compileJava"].dependsOn(project(":javac").tasks["build"])
