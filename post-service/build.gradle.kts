plugins {
	java
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "tproject"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenLocal()
	mavenCentral()
}


dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("tproject:common-proto:1.0.0")
	implementation("io.grpc:grpc-netty-shaded:1.61.0")
	implementation("io.grpc:grpc-stub:1.61.0")
	implementation("io.grpc:grpc-protobuf:1.61.0")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-cassandra")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("com.amazonaws:aws-java-sdk-s3:1.12.643")
	implementation("com.amazonaws:aws-java-sdk-cloudfront:1.12.643")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	implementation("tproject:t-common:t-common-0.0.1")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
