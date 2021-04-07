plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":language-java"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("compiler-embeddable"))
}
