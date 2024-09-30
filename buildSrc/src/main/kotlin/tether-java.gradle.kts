plugins {
    `java-library`
}

java {
    toolchain {
        // target java 8
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

dependencies {
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