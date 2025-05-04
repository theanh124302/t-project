import com.google.protobuf.gradle.*

plugins {
	java
	id("org.springframework.boot") version "3.4.5"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.google.protobuf") version "0.9.4"
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
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("io.grpc:grpc-netty-shaded")
	implementation("io.grpc:grpc-protobuf")
	implementation("io.grpc:grpc-stub")
	implementation("com.google.protobuf:protobuf-java")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

val grpcVersion = "1.61.0"
val protobufVersion = "3.25.1"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("javax.annotation:javax.annotation-api:1.3.2")
	implementation("io.grpc:grpc-netty-shaded:$grpcVersion")
	implementation("io.grpc:grpc-protobuf:$grpcVersion")
	implementation("io.grpc:grpc-stub:$grpcVersion")
	implementation("com.google.protobuf:protobuf-java:$protobufVersion")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:$protobufVersion"
	}
	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
		}
	}
	generateProtoTasks {
		all().forEach {
			it.plugins {
				id("grpc")
			}
		}
	}
}

sourceSets["main"].proto.srcDir("src/main/proto")

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.processResources {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
	exclude("**/*.proto")
}