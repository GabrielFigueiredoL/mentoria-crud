plugins {
    kotlin("jvm") version "2.1.10"
}

group = "org.gabrielfigueiredol"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.postgresql:postgresql:42.7.7")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}