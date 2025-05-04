import com.google.protobuf.gradle.*

plugins {
    id("java")
    id("maven-publish")                             // 👈 Thêm plugin này để publish
    id("com.google.protobuf") version "0.9.4"
}

group = "tproject"
version = "1.0.0"                                    // 👈 Rất quan trọng: đặt version rõ ràng

val grpcVersion = "1.61.0"
val protobufVersion = "3.25.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.grpc:grpc-stub:$grpcVersion")
    implementation("io.grpc:grpc-protobuf:$grpcVersion")
    implementation("com.google.protobuf:protobuf-java:$protobufVersion")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
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

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "tproject"
            artifactId = "common-proto"
            version = "1.0.0"
        }
    }
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    exclude("**/*.proto")
}
