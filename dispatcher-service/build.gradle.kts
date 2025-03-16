plugins {
    java
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "dev.babal"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2024.0.0"

dependencies {
//	implementation("org.springframework.boot:spring-boot-starter")
//	implementation("org.springframework.cloud:spring-cloud-function-context")
    // ^^^____ included on cloud stream
    implementation("org.springframework.cloud:spring-cloud-starter-stream-rabbit")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.springframework.cloud:spring-cloud-stream-test-binder")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

//    testImplementation("org.springframework.cloud:spring-cloud-stream")
//        artifact {
//            name = "spring-cloud-stream"
//            extension = "jar"
//            type = "test-jar"
//            classifier = "test-binder"
//        }
//    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
