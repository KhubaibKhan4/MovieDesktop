import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    //id("org.jetbrains.compose")
    id("org.jetbrains.compose") version "1.5.0-dev1114"
    kotlin("plugin.serialization") version "1.8.20"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation(compose.desktop.common)
    implementation(compose.material3)
    implementation(compose.ui)
    implementation(compose.uiTooling)
    implementation(compose.preview)
    implementation(compose.animationGraphics)
    implementation(compose.materialIconsExtended)

    implementation("io.ktor:ktor-client-cio-jvm:2.3.5")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.5")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("io.ktor:ktor-server-default-headers:2.3.5")
    implementation("ch.qos.logback:logback-classic:1.2.9")
    implementation("media.kamel:kamel-image:0.8.2")
    implementation("net.java.dev.jna:jna:5.9.0")

}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "MovieDesktop"
            packageVersion = "1.0.0"
            windows {
                packageVersion = "1.0.0"
                msiPackageVersion = "1.0.0"
                exePackageVersion = "1.0.0"
            }
        }
    }
}
