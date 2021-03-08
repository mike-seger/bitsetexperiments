import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"
}

group = "com.example"
version = "0.0.2-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("net.minidev:json-smart:2.3")
	implementation("com.google.guava:guava:30.1-jre")
	implementation("org.itadaki:bzip2:0.9.1")
	//implementation("org.apache.commons:commons-compress:1.20")

	implementation("org.springdoc:springdoc-openapi-webflux-ui:1.5.5")
	implementation("org.springdoc:springdoc-openapi-kotlin:1.5.5")

	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.12.2")

	implementation("org.springframework.boot:spring-boot-starter-webflux")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
