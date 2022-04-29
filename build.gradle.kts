import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import nu.studer.gradle.jooq.JooqEdition

plugins {
	id("org.springframework.boot") version "2.6.7"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("nu.studer.jooq") version "5.2.1"
	id("org.ajoberstar.grgit") version "4.1.1"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "com.translator"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	jooqGenerator("mysql:mysql-connector-java")
	api("org.springframework.boot:spring-boot-starter-web")
	api("org.springframework.boot:spring-boot-starter-jooq")
	runtimeOnly("mysql:mysql-connector-java")
	testRuntimeOnly("mysql:mysql-connector-java")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jooq {
	version.set("3.14.8")
	edition.set(JooqEdition.OSS)

	configurations {
		create("translator") {
			generateSchemaSourceOnCompilation.set(false)

			jooqConfiguration.apply {
				logging = org.jooq.meta.jaxb.Logging.WARN
				jdbc = jdbc.apply {
					driver = "com.mysql.cj.jdbc.Driver"
					url = "jdbc:mysql://127.0.0.1:3306"
					username = "root"
					password = "root"
				}
				generator = generator.apply {
					name = "org.jooq.codegen.JavaGenerator"
					database.apply {
						name = "org.jooq.meta.mysql.MySQLDatabase"
						inputSchema = "translator"
					}
					target.apply {
						packageName = "org.jooq.translator"
						directory = "src/main/java"
					}
				}
			}
		}
	}
}
