plugins {
    id("tether-java")
    `maven-publish`
}

java {
    withSourcesJar()
}

// un-wire sourcesJar from normal assemble
tasks.named("assemble") {
    setDependsOn(dependsOn.filterNot {
        (it as? Named)?.name in listOf("sourcesJar")
    })
}

tasks.withType<AbstractPublishToMaven> {
    dependsOn.add(tasks.named("sourcesJar"))
}

afterEvaluate {
    publishing {
        val publicationName = name
        publications.create<MavenPublication>(publicationName) {
            // if are on an Actions runner, set up GitHub Packages
            if (System.getenv("GITHUB_ACTIONS") == "true") {
                repositories {
                    maven {
                        name = "GitHubPackages"
                        url = uri("https://maven.pkg.github.com/the-h-team/Tether")
                        credentials {
                            username = System.getenv("GITHUB_ACTOR")
                            password = System.getenv("GITHUB_TOKEN")
                        }
                    }
                }
            }
            pom {
                description.set(
                    project.description.takeIf { it != rootProject.description } ?:
                    throw IllegalStateException("Set the project description in ${project.projectDir.name}/build.gradle.kts before activating publishing.")
                )
                url.set(project.properties["url"] as String)
                inceptionYear.set(project.properties["inceptionYear"] as String)
                organization {
                    name.set("Sanctum")
                    url.set("https://github.com/the-h-team")
                }
                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://opensource.org/licenses/Apache-2.0")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("ms5984")
                        name.set("Matt")
                        url.set("https://github.com/ms5984")
                    }
                    developer {
                        id.set("Hempfest")
                        name.set("Hempfest")
                        url.set("https://github.com/Hempfest")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/the-h-team/Tether.git")
                    developerConnection.set("scm:git:ssh://github.com/the-h-team/Tether.git")
                    url.set("https://github.com/the-h-team/Tether/tree/main")
                }
            }
            from(components["java"])
        }
    }
}