plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation("ch.qos.logback:logback-classic:${property("logbackVersion")}")
    implementation("com.google.guava:guava:${property("guavaVersion")}")
    implementation("com.zaxxer:HikariCP:${property("hikariCPVersion")}")
    implementation("org.springframework:spring-tx:${property("springTxVersion")}")
    testImplementation("org.testng:testng:${property("testNgVersion")}")
    testImplementation("org.junit.jupiter:junit-jupiter:${property("junitVersion")}")
    testImplementation("org.assertj:assertj-core:${property("assertjVersion")}")
}