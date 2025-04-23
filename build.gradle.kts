plugins {
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.serialization") version "1.8.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(path=":data"))
    implementation(project(path=":domain"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
}


tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(22)
}