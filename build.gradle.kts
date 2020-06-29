import com.jfrog.bintray.gradle.BintrayExtension.*
import com.jfrog.bintray.gradle.tasks.*
import groovy.lang.*
import org.jfrog.gradle.plugin.artifactory.dsl.*
import org.jfrog.gradle.plugin.artifactory.task.*

plugins {
    `maven-publish`
    java
    id("com.jfrog.bintray") version "1.8.4"
    id("com.jfrog.artifactory") version "4.10.0"
}

tasks.withType(Wrapper::class.java) {
    gradleVersion = "6.0"
}

val bintrayUser: String? by project
val bintrayKey: String? by project
val version: String? by project

group = "io.github.art"

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}

subprojects {
    group = rootProject.group

    repositories {
        jcenter()
        mavenCentral()
        maven {
            url = uri("https://oss.jfrog.org/oss-snapshot-local")
        }
    }

    apply(plugin = "java")
    apply(plugin = "com.jfrog.bintray")
    apply(plugin = "com.jfrog.artifactory")
    apply(plugin = "maven-publish")

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.12")
        annotationProcessor("org.projectlombok:lombok:1.18.12")
        testCompileOnly("org.projectlombok:lombok:1.18.12")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.12")
    }

    if (bintrayUser.isNullOrEmpty() || bintrayKey.isNullOrEmpty()) {
        return@subprojects
    }

    afterEvaluate {
        val jar: Jar by tasks
        val sourceJar = task("sourceJar", type = Jar::class) {
            archiveClassifier.set("sources")
            from(sourceSets.main.get().allJava)
        }

        publishing {
            publications {
                create<MavenPublication>(project.name) {
                    artifact(jar)
                    artifact(sourceJar)
                    groupId = rootProject.group as String
                    artifactId = project.name
                    version = rootProject.version as String
                    pom {
                        packaging = "jar"
                        description.set(project.name)
                    }
                }
            }
        }

        artifactory {
            setContextUrl("https://oss.jfrog.org")
            publish(delegateClosureOf<PublisherConfig> {
                repository(delegateClosureOf<GroovyObject> {
                    setProperty("repoKey", "oss-snapshot-local")
                    setProperty("username", bintrayUser ?: "")
                    setProperty("password", bintrayKey ?: "")
                    setProperty("maven", true)
                })
            })
            val artifactoryPublish: ArtifactoryTask by tasks
            with(artifactoryPublish) {
                publications(project.name)
            }
            artifactoryPublish.dependsOn(tasks["generatePomFileFor${name.capitalize()}Publication"], jar, sourceJar)
        }

        bintray {
            user = bintrayUser ?: ""
            key = bintrayKey ?: ""
            publish = true
            override = true
            setPublications(project.name)
            pkg(delegateClosureOf<PackageConfig> {
                repo = "art-generator"
                name = rootProject.group as String
                userOrg = "art-community"
                websiteUrl = "https://github.com/art-community/art-generator"
                vcsUrl = "https://github.com/art-community/art-generator"
                setLabels("javac")
                setLicenses("Apache-2.0")
            })
            with(tasks["bintrayUpload"] as BintrayUploadTask) {
                publish = true
                dependsOn(tasks["generatePomFileFor${this@subprojects.name.capitalize()}Publication"], jar, sourceJar)
            }
        }
    }

}

afterEvaluate {
    tasks["bintrayUpload"].enabled = false
    tasks["bintrayPublish"].enabled = false
    tasks["artifactoryPublish"].enabled = false
}
