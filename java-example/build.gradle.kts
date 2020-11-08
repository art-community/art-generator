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
}
tasks["compileJava"].dependsOn("clean").dependsOn(project(":java-generator").tasks["build"])