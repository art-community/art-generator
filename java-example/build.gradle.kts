buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.20")
    }
}

apply(plugin = "kotlin")

dependencies {
    implementation(project(":java-generator"))
    implementation(project(":core"))
    implementation(project(":server"))
    implementation(project(":value"))
    implementation(project(":logging"))
    implementation(project(":launcher"))
    implementation(project(":model"))
    implementation(project(":xml"))
    implementation(project(":rsocket"))
    implementation(project(":json"))
    implementation(project(":protobuf"))
    implementation(project(":message-pack"))
    annotationProcessor(project(":java-generator"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:+")
}
tasks["compileJava"].dependsOn("clean").dependsOn(project(":java-generator").tasks["build"])

tasks.withType<org.gradle.api.tasks.compile.JavaCompile> {
    this.options.compilerArgs.add("-Xlint")
}
