import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.asciidoctor.convert") version "1.5.8"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
}

group = "com.sneakers_monitor"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_14

repositories {
    mavenCentral()
}

val snippetsDir by extra {file("build/generated-snippets")}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("mysql:mysql-connector-java")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    implementation("org.jsoup:jsoup:1.14.3")
    runtimeOnly("com.h2database:h2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.6")
    testImplementation("io.mockk:mockk:1.12.3")

    // Slack Block Kit
    implementation("com.slack.api:slack-api-client:1.8.1")
    implementation("com.slack.api:slack-api-model:1.8.1")
    implementation("com.slack.api:slack-app-backend:1.8.1")
    implementation("com.squareup.okhttp3:okhttp:4.2.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "14"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    outputs.dir(snippetsDir)
}

tasks.asciidoctor {
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
}

tasks.bootJar {
    archiveBaseName.set("sneakers-monitor-crawler")
    archiveFileName.set("sneakers-monitor-crawler-staging.jar")
    archiveVersion.set("0.0.0")
    mainClass.set("com.sneakers_monitor.crawler.CrawlerApplication.kt")
}