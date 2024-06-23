import org.springframework.boot.gradle.tasks.run.BootRun
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    java
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2023.0.1"
extra["testcontainersVersion"] = "1.19.8"

tasks.named<BootRun>("bootRun") {
    systemProperty("spring.profiles.active", "testdata")
}

tasks.named<BootBuildImage>("bootBuildImage") {
    builder = "docker.io/paketobuildpacks/builder-jammy-base"
    imageName = "${project.name}"
    environment = mapOf("BP_JVM_VERSION" to "21.*")

    docker {
        publishRegistry {
            username = project.findProperty("registryUsername").toString()
            password = project.findProperty("registryToken").toString()
            url = project.findProperty("registryUrl").toString()
        }
    }
}

configurations {
    compileOnly {
        annotationProcessor
    }
}


group = "com.polarbookshop"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.retry:spring-retry")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")


    runtimeOnly("org.postgresql:postgresql")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.testcontainers:postgresql")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
