plugins {
    java
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.netflix.dgs.codegen") version "6.2.1"
}

group = "dev.amirgol"
version = "1.0.0"
description = "mini-notion"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-mail")

    // Spring Modulith dependencies
    implementation("org.springframework.modulith:spring-modulith-starter-core")

    // MinIO SDK - Bucket Storage
    implementation("io.minio:minio:8.5.17")


    // Database
    implementation("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core:10.20.0")
    implementation("org.flywaydb:flyway-database-postgresql")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // Swagger / OpenAPI
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    // DGS BOM + Starter
    implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:9.1.3"))
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")

    // Utilities
    implementation("me.paulschwarz:spring-dotenv:3.0.0")
    implementation("ch.qos.logback:logback-classic")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Test
    runtimeOnly("com.h2database:h2")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.springframework.graphql:spring-graphql-test") // only for testing GraphQL endpoints
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.modulith:spring-modulith-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencyManagement {
    imports {
        mavenBom("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:9.1.3")
        mavenBom("org.springframework.modulith:spring-modulith-bom:1.1.0")
    }
}
