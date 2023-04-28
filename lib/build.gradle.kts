plugins {
    java
    kotlin("jvm") version "1.8.10"
    jacoco
    `java-library`
    `maven-publish`
    kotlin("plugin.serialization") version "1.5.0"
    // checkstyle
    id("org.jetbrains.compose") version "1.4.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenLocal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    mavenCentral()
    google()
}

dependencies {
    api("org.apache.commons:commons-math3:3.6.1")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    implementation("com.google.code.gson:gson:2.10.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.0")

    // Neo4j
    val neo4jCore = "4.0.5"
    implementation("org.neo4j", "neo4j-ogm-core", neo4jCore)
    implementation("org.neo4j", "neo4j-ogm-bolt-driver", neo4jCore)

    // JDBC Sqlite
    val sqliteJdbcVersion: String by project
    implementation("org.xerial", "sqlite-jdbc", sqliteJdbcVersion)

    // JetBrains Exposed
    val exposedVersion: String by project
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    implementation(kotlin("stdlib-jdk8"))
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.8.10")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
    implementation(kotlin("stdlib-jdk8"))

}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
    val failedTests = mutableListOf<TestDescriptor>()
    val skippedTests = mutableListOf<TestDescriptor>()

    addTestListener(object : TestListener {
        override fun beforeSuite(suite: TestDescriptor?) {}
        override fun beforeTest(testDescriptor: TestDescriptor?) {}
        override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {
            when (result.resultType) {
                TestResult.ResultType.FAILURE -> failedTests.add(testDescriptor)
                TestResult.ResultType.SKIPPED -> skippedTests.add(testDescriptor)
                else -> {}
            }
        }

        override fun afterSuite(suite: TestDescriptor, result: TestResult) {
            if (suite.parent == null) { // root suite
                logger.lifecycle("####################################################################################")
                logger.lifecycle("Test result: ${result.resultType}")
                logger.lifecycle(
                    "Test summary: ${result.testCount} tests, " +
                            "${result.successfulTestCount} succeed, " +
                            "${result.skippedTestCount} skipped, " +
                            "${result.failedTestCount} failed."
                )
            }
        }
    })

}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        xml.outputLocation.set(layout.buildDirectory.file("jacoco/report.xml"))
        csv.required.set(true)
        csv.outputLocation.set(layout.buildDirectory.file("jacoco/report.csv"))
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

tasks.jacocoTestCoverageVerification {
    classDirectories.setFrom(classDirectories.files.flatMap {
        fileTree(it) {
            include("**/treelib/**")
            exclude(
                "**/commonObjects/**",
                "**/RBVertex.class",
                "**/AVLVertex.class",
                "**/BINVertex.class",
                "**/Vertex.class"
            )
        }
    })
    dependsOn(tasks.jacocoTestReport)
    violationRules {
        rule {
            element = "CLASS"
            limit {
                counter = "BRANCH"
                minimum = 0.5.toBigDecimal()
            }
        }
        rule {
            element = "CLASS"
            limit {
                counter = "LINE"
                minimum = 0.6.toBigDecimal()
            }
        }
        rule {
            element = "CLASS"
            limit {
                counter = "METHOD"
                minimum = 0.9.toBigDecimal()
            }
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "Tree"
            artifactId = "lib"
            version = "1.1"
            from(components["java"])
        }
    }
}
