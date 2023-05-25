plugins {
    java
    kotlin("jvm") version "1.8.10"
    id("org.jetbrains.compose") version "1.4.0"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    api("org.apache.commons:commons-math3:3.6.1")
    implementation(project(":lib"))
    implementation("com.google.guava:guava:31.1-jre")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    implementation("com.google.code.gson:gson:2.10.1")

    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
}

compose.desktop {
    application {
        mainClass = "treeApp.MainKt"
    }
}
