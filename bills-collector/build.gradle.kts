import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
}

group = "com.quicklybly"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
    maven("https://repo.spring.io/snapshot")
    maven("https://repo.spring.io/milestone")
}

val springAiVersion by extra("1.0.0-M1")
dependencies {
    // spring
    implementation("org.springframework.boot", "spring-boot-starter-actuator")
    implementation("org.springframework.boot", "spring-boot-starter-data-jpa")
    implementation("org.springframework.boot", "spring-boot-starter-web")
    implementation("org.springframework.boot", "spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot", "spring-boot-starter-validation")
    implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter")

    // kotlin
    implementation("com.fasterxml.jackson.module", "jackson-module-kotlin")
    implementation("com.fasterxml.jackson.module", "jackson-module-blackbird")
    implementation("org.jetbrains.kotlin", "kotlin-reflect")

    // db
    implementation("org.liquibase", "liquibase-core", libs.versions.liquibase.get())
    runtimeOnly("org.postgresql", "postgresql", libs.versions.postgres.get())

    // other
    implementation("org.springdoc", "springdoc-openapi-starter-webmvc-ui", libs.versions.swagger.get())
    implementation("io.github.microutils", "kotlin-logging", libs.versions.kotlinLogging.get())

    // tests
    testImplementation("org.springframework.boot", "spring-boot-starter-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:$springAiVersion")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
