plugins {
    `java-library`
}

java {
    toolchain {
        // target java 8
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

repositories.mavenCentral()
dependencies {
    // annotations: https://central.sonatype.com/artifact/org.jetbrains/annotations
    api("org.jetbrains:annotations:25.0.0")
    // junit
    testImplementation(platform("org.junit:junit-bom:5.11.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}