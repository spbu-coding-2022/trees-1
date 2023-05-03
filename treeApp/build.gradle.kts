plugins {
    java
    kotlin("jvm") version "1.8.10"
    jacoco
    id("org.jetbrains.compose") version "1.4.0"
}

kotlin {
    jvmToolchain(17)
}

repositories {
    mavenLocal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    mavenCentral()
}

dependencies {
    api("org.apache.commons:commons-math3:3.6.1")
    implementation(project(":lib"))
    implementation("com.google.guava:guava:31.1-jre")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    implementation("com.google.code.gson:gson:2.10.1")

    implementation(compose.desktop.currentOs)
    implementation(compose.material3)

    implementation(kotlin("stdlib-jdk8"))
}


compose.desktop {
    application {
        mainClass = "Main"
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

    addTestListener (object: TestListener {
        override fun beforeSuite(suite: TestDescriptor?) { }
        override fun beforeTest(testDescriptor: TestDescriptor?) { }
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
    classDirectories.setFrom( classDirectories.files.flatMap { fileTree(it) {
        include("**/RBBalancer.class", "**/AVLBalancer.class", "**/BINStruct", "**/AVLStruct.class", "**/BINStruct.class")
        exclude("**/singleObjects/**", "**/RBVertex.class", "**/AVLVertex.class", "**/BINVertex.class", "**/Vertex.class", "**/BINStruct.class", "**/AVLStruct.class", "**/RBStruct.class", "**/TreeStruct.class")
    } })
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
