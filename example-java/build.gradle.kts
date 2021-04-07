plugins {
    id("java-generator")
}

val lombokVersion: String by project

dependencies {
    implementation("io.art.java:core")
    implementation("io.art.java:scheduler")
    implementation("io.art.java:configurator")
    implementation("io.art.java:server")
    implementation("io.art.java:communicator")
    implementation("io.art.java:value")
    implementation("io.art.java:logging")
    implementation("io.art.java:launcher")
    implementation("io.art.java:model")
    implementation("io.art.java:xml")
    implementation("io.art.java:rsocket")
    implementation("io.art.java:json")
    implementation("io.art.java:protobuf")
    implementation("io.art.java:message-pack")
    implementation("io.art.java:yaml")
    implementation("io.art.java:graal")

    compileOnly("org.projectlombok", "lombok", lombokVersion)
    annotationProcessor(project(":language-java"))
    annotationProcessor("org.projectlombok", "lombok", lombokVersion)
}
