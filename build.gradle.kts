import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.2.20"
    application
}

group = "dev.anton"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.minestom:minestom:2025.10.31-1.21.10")
    implementation("ch.qos.logback:logback-classic:1.5.21")
    implementation("net.kyori:adventure-text-minimessage:4.25.0")
    implementation("com.github.docker-java:docker-java-core:3.3.0")
    implementation("com.github.docker-java:docker-java-transport-httpclient5:3.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

kotlin {
    jvmToolchain(25)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.JVM_24)
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("dev.anton.MainKt")
    applicationDefaultJvmArgs += listOf(
        "--sun-misc-unsafe-memory-access=allow"
    )
}