plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:${property("logbackVersion")}")
}

tasks.test {
    useJUnitPlatform()
}