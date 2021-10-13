import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    id("maven-publish")
}

group = "net.neverstopgaming"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    withSourcesJar()
}

if (System.getProperty("publishName") != null && System.getProperty("publishPassword") != null) {
    publishing {
        publications {
            create<MavenPublication>(project.name) {
                groupId = Properties.group
                artifactId = project.name
                version = Properties.version
                from(components["java"])
                pom {
                    name.set(project.name)
                    url.set("https://github.com/NeverStopGaming/ExceptionUtil")
                    properties.put("inceptionYear", "2021")
                    licenses {
                        license {
                            name.set("All Rights Reserved")
                            url.set("All Rights Reserved")
                            distribution.set("repo")
                        }
                    }
                    developers {
                        developer {
                            id.set("Infinity_dev")
                            name.set("Florin Dornig")
                            email.set("infinitydev@NeverStopGaming.net")
                        }
                        developer {
                            id.set("Chaoten")
                            name.set("Ben Kießig")
                            email.set("chaoten@NeverStopGaming.net")
                        }
                    }
                }
            }
            repositories {
                maven("https://repo.NeverStopGaming.net/repository/maven-internal/") {
                    this.name = "nsg-internal"
                    credentials {
                        this.password = System.getProperty("publishPassword")
                        this.username = System.getProperty("publishName")
                    }
                }
            }
        }
    }
}
tasks {

    test {
        useJUnitPlatform()
    }

    //Set the Name of the Sources Jar
    kotlinSourcesJar {
        archiveFileName.set("${project.name}-${Properties.version}-${getCommitHash()}-sources.jar")
        doFirst {
            //Set Manifest
            manifest {
                attributes["Implementation-Title"] = project.name
                attributes["Implementation-Version"] = Properties.version
                attributes["Specification-Version"] = Properties.version
                attributes["Implementation-Vendor"] = "NeverStopGaming.net"
                attributes["Built-By"] = System.getProperty("user.name")
                attributes["Build-Jdk"] = System.getProperty("java.version")
                attributes["Created-By"] = "Gradle ${gradle.gradleVersion}"
                attributes["Commit-Hash"] = getCommitHash()
            }
        }
    }

    jar {
        archiveFileName.set("${project.name}-${Properties.version}-${getCommitHash()}.jar")
        doFirst {
            //Set Manifest
            manifest {
                attributes["Implementation-Title"] = project.name
                attributes["Implementation-Version"] = Properties.version
                attributes["Specification-Version"] = Properties.version
                attributes["Implementation-Vendor"] = "NeverStopGaming.net"
                attributes["Built-By"] = System.getProperty("user.name")
                attributes["Build-Jdk"] = System.getProperty("java.version")
                attributes["Created-By"] = "Gradle ${gradle.gradleVersion}"
                attributes["Commit-Hash"] = getCommitHash()
            }
        }
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "16"
    }

    withType<JavaCompile> {
        this.options.encoding = "UTF-8"
    }
}
